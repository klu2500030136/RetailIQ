package m3_dp;

import core.RetailDataStore;
import model.Product;

public class ProductAlignmentEngine {
    
    public enum AlignmentType {
        GLOBAL_NEEDLEMAN_WUNSCH,
        LOCAL_SMITH_WATERMAN
    }

    private final RetailDataStore dataStore;

    public ProductAlignmentEngine(RetailDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public AlignmentResult alignProductDescriptions(String productIdA, String productIdB, AlignmentType type) {
        Product pA = findProduct(productIdA);
        Product pB = findProduct(productIdB);
        
        if (pA == null || pB == null) return null;
        
        String descA = pA.getDescription();
        String descB = pB.getDescription();
        
        if (type == AlignmentType.GLOBAL_NEEDLEMAN_WUNSCH) {
            NeedlemanWunsch nw = new NeedlemanWunsch(2, -1, -2);
            return nw.align(descA, descB);
        } else {
            SmithWaterman sw = new SmithWaterman(2, -1, -2);
            return sw.align(descA, descB);
        }
    }
    
    public AlignmentResult alignText(String textA, String textB, AlignmentType type) {
        if (type == AlignmentType.GLOBAL_NEEDLEMAN_WUNSCH) {
            NeedlemanWunsch nw = new NeedlemanWunsch(2, -1, -2);
            return nw.align(textA, textB);
        } else {
            SmithWaterman sw = new SmithWaterman(2, -1, -2);
            return sw.align(textA, textB);
        }
    }
    
    private Product findProduct(String id) {
        for (Product p : dataStore.getProducts()) {
            if (p.getProductId().equals(id)) return p;
        }
        return null;
    }
}
