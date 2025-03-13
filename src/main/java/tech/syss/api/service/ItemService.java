package tech.syss.api.service;

import tech.syss.api.model.Item;
import tech.syss.api.repository.ItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.Cacheable;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Cacheable(value = "itemList", key = "'page:' + #pageable.pageNumber + ',size:' + #pageable.pageSize + ',filters:' + #filters")
    public Object all(Specification<Item> spec, Pageable pageable) {
        return itemRepository.findAll(spec, pageable);
    }

    @Cacheable(value = "items", key = "#id", unless = "#result == null")
    public Object get(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Caching(evict = {
        @CacheEvict(value = "items", key = "#item.id", condition = "#item.id != null", beforeInvocation = true),
        @CacheEvict(value = "itemList", allEntries = true)
    })
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Caching(evict = {
        @CacheEvict(value = "items", key = "#id", beforeInvocation = true),
        @CacheEvict(value = "itemList", allEntries = true)
    })
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
