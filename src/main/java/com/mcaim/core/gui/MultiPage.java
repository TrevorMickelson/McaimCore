package com.mcaim.core.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for handling
 * a multi page system for a gui
 *
 * NOT BEING USED YET
 */
public class MultiPage {
    private int page;                                                   // Current page player is on
    private int maxItemsPerPage;                                        // Max items allowed per page
    private int maxTotalPages;                                          // Max total pages that are possible
    private final List<ItemStack> items;                                // Items inside the inventory

    public MultiPage() {
        this.page = 1;
        this.maxItemsPerPage = 54;
        this.maxTotalPages = 1;
        this.items = new ArrayList<>();
    }

    public int getPage() { return this.page; }
    public void setPage(int page) { this.page = page; }

    public int getMaxItemsPerPage() { return this.maxItemsPerPage; }
    public void setMaxItemsPerPage(int maxItemsPerPage) { this.maxItemsPerPage = maxItemsPerPage; }

    public int getMaxTotalPages() { return this.maxTotalPages; }
    public void setMaxTotalPages(int maxTotalPages) { this.maxTotalPages = maxTotalPages; }
    public void updateMaxTotalPages() {
        double itemCount = items.size() <= 0 ? 1 : items.size();
        double maxPerPage = maxItemsPerPage;
        double maxTotal = itemCount / maxPerPage;
        setMaxTotalPages(maxTotal > (int) maxTotal ? (int) maxTotal + 1 : (int) maxTotal);

        // Updating page counter
        if (page > maxTotalPages)
            setPage(getPage() - 1);
    }

    public List<ItemStack> getItems() { return this.items; }

    /**
     * This method determines what
     * list to return based on the page value
     *
     * This is an easy way for me to
     * manage a multipage system (if it comes to that)
     *
     * @return list of any object
     */
    public <T> List<T> listFromPageCount(int page, int maxItems, List<T> referenceList) {
        int secondValue = page * maxItems;
        int firstValue = secondValue - maxItems;
        return referenceList.isEmpty() ? referenceList : referenceList.subList(firstValue, Math.min(referenceList.size(), secondValue));
    }
}
