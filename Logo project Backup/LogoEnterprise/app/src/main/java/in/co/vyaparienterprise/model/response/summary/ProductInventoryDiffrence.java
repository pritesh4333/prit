package in.co.vyaparienterprise.model.response.summary;

import java.util.ArrayList;

import in.co.vyaparienterprise.model.Lines;

public class ProductInventoryDiffrence {


    ArrayList<ProductInventory> lines = new ArrayList<ProductInventory>();

    public ArrayList<ProductInventory> getLines() {
        return lines;
    }

    public void setLines(ArrayList<ProductInventory> lines) {
        this.lines = lines;
    }
}
