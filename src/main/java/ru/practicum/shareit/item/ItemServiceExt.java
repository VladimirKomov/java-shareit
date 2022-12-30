package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.AbstractService;
import ru.practicum.shareit.booking.model.Storage;
import ru.practicum.shareit.user.User;

@Service
public class ItemServiceExt extends AbstractService<Item> {

    @Autowired
    public ItemServiceExt(Storage<Item> storage) {
        this.storage = storage;
    }
    @Override
    protected void validate(Item data) {

    }

    @Override
    protected void updateValues(long id, Item data) {

    }
}
