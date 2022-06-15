package org.jrutten.orderproject.item;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    private final Logger logger;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO addItem(@RequestBody CreateItemDTO createItemDTO){
        logger.info("addItem postRequest: " + createItemDTO.toString());
        return this.itemService.addItem(createItemDTO);
    }
}
