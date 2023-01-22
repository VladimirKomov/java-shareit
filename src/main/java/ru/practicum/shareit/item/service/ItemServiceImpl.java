package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;
import static ru.practicum.shareit.item.CommentMapper.MAP_COMMENT;
import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;
import static ru.practicum.shareit.user.UserMapper.MAP_USER;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;


    public ItemDtoResponse create(long userId, ItemDto data) {
        data.setOwner(MAP_USER.toUser(userService.get(userId)));
        return MAP_ITEM.toItemDtoResponse(
                itemRepository.save(MAP_ITEM.toItem(data)));
    }

    public ItemDtoResponseLong get(long userId, long itemId) {
        ItemDtoResponseLong itemDtoResponseLong = MAP_ITEM.toItemDtoRespLong(
                itemRepository.findById(itemId).orElseThrow(NotFoundException::new));
        return setBookings(itemDtoResponseLong, userId);
    }

    public Item getEntity(long id) {
        return itemRepository.findById(id).orElseThrow(NotFoundException::new);
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

    @Override
    public CommentDtoResponse create(long userId, long itemId, CommentDto commentDto) {
        Collection<Booking> bookings = bookingRepository
                .findAllByBookerIdAndItemIdAndStatusAndStartBeforeOrderByStartDesc(userId, itemId,
                        StatusBooking.APPROVED, LocalDateTime.now());
        if (bookings.size() == 0) throw new BadRequestException("Booking not found");

        Comment comment = MAP_COMMENT.toComment(commentDto);
        Booking booking = bookings.stream().toList().get(0);
        comment.setUser(booking.getBooker());
        comment.setItem(booking.getItem());
        comment.setCreated(LocalDateTime.now());
        return MAP_COMMENT.toDtoResponse(commentRepository.save(comment));
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
        Booking bookingLast = bookingRepository.findByItemIdAndItemOwnerIdAndEndIsBeforeOrderByStart(
                itemDtoLong.getId(), userId, LocalDateTime.now());
        Booking bookingNext = bookingRepository.findByItemIdAndItemOwnerIdAndStartIsAfterOrderByStart(
                itemDtoLong.getId(), userId, LocalDateTime.now());
        Collection<Comment> comments = commentRepository.findAllByItemIdOrderByCreatedDesc(itemDtoLong.getId());

        itemDtoLong.setLastBooking(MAP_BOOKING.toBookingDtoRepository(bookingLast));
        itemDtoLong.setNextBooking(MAP_BOOKING.toBookingDtoRepository(bookingNext));
        itemDtoLong.setComments(comments.stream().
                map(MAP_COMMENT::toDtoResponse)
                .collect(Collectors.toList()));

        return itemDtoLong;
    }
}
