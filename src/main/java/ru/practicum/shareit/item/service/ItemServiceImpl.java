package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.dto.ItemDtoResponseLong;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;
import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;


    public ItemDtoResponse create(long userId, ItemDto data) {
        data.setOwner(userService.get(userId));
        return MAP_ITEM.toItemDtoResponse(
                itemRepository.save(MAP_ITEM.toItem(data)));
    }

    public ItemDtoResponseLong get(long userId, long itemId) {
        ItemDtoResponseLong itemDtoResponseLong = MAP_ITEM.toItemDtoRespLong(
                itemRepository.findById(itemId).orElseThrow(NotFoundException::new));
        return  setBookings(itemDtoResponseLong, userId);
    }

    public Item getEntity(long id) {
        return  itemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public ItemDtoResponse update(long userId, long id, ItemDto data) {
        validate(userId,
                MAP_ITEM.toItemDto(itemRepository.findById(id).orElseThrow(NotFoundException::new)));
        return MAP_ITEM.toItemDtoResponse(
                itemRepository.save(updateValues(id, MAP_ITEM.toItem(data))));
    }

    protected Item updateValues(long id, Item data) {
        var target = itemRepository.findById(id).orElseThrow(NotFoundException::new);
        MAP_ITEM.update(data, target);

        return target;
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    public int getSize() {
        //return itemRepository.count;
        return 0;
    }

    protected void validate(long userId, ItemDto data) {
        if (userId !=
                itemRepository.findById(data.getId()).orElseThrow(NotFoundException::new).getOwner().getId()) {
            throw new NotFoundException();
        }
    }

    public Collection<ItemDtoResponseLong> getAllItemByUserId(long userId) {
        return itemRepository.findItemsByOwnerIdOrderById(userId).stream()
                .map(MAP_ITEM::toItemDtoRespLong)
                .map(i -> setBookings(i, userId))
                .collect(Collectors.toList());
    }

    public Collection<ItemDtoResponse> getBySubstring(String substr) {
        return substr.isBlank() ? List.of() : itemRepository.searchAvailableByNameAndDescription(substr).stream()
                .map(MAP_ITEM::toItemDtoResponse)
                .collect(Collectors.toList());
    }

    private ItemDtoResponseLong setBookings(ItemDtoResponseLong itemDtoLong, long userId) {
        Booking bookingLast = bookingRepository.findByItemIdAndItemOwnerIdAndEndIsBeforeOrderByStart(itemDtoLong.getId(), userId,
                LocalDateTime.now());

        Booking bookingNext = bookingRepository.findByItemIdAndItemOwnerIdAndStartIsAfterOrderByStart(itemDtoLong.getId(), userId,
                LocalDateTime.now());

        itemDtoLong.setLastBooking(MAP_BOOKING.toBookingDtoRepository(bookingLast));
        itemDtoLong.setNextBooking(MAP_BOOKING.toBookingDtoRepository(bookingNext));

        return itemDtoLong;
    }
}
