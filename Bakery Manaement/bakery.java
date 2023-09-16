import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class bakery extends Frame implements ItemListener,ActionListener {
    Choice itemList,quantity;
    List items,price,selectedItems,selectedPrice,selectedQuantity,total;
    Button addToCartBtn,removeFromCartBtn,totalBtn,exitBtn;
    TextField fullTotalAmt;
    int grandTotal =0;
    bakery(){
        //variables
        Label heading;

        //frame
        setSize(1380,1000);
        setLayout(new FlowLayout(FlowLayout.LEFT,46,30));
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        //heading
        heading = new Label("Welcome to Bakery Point");
        heading.setFont(new Font("Monospaced",Font.BOLD,60));
        add(heading);

        //itemList choice
        itemList = new Choice();
        itemList.add("---SELECT ITEM---");
        itemList.add("Cakes");
        itemList.add("Cool Drinks");
        itemList.add("Ice Creams");
        itemList.add("Chips");
        itemList.add("Other Items");
        itemList.setPreferredSize(new Dimension(300,50));
        
        itemList.setFont(new Font("Monospaced",Font.PLAIN,20));
        add(itemList);
        itemList.addItemListener(this);

        //items
        items = new List();
        items.setPreferredSize(new Dimension(600,100));
        add(new Label("Select ITEMS :"));
        add(items);
        items.setFont(new Font("Calibri",Font.PLAIN,20));
        items.addItemListener(this);


        //price
        price = new List();
        price.addItemListener(this);
        price.setFont(new Font("Calibri",Font.PLAIN,20));
        add(new Label("Select PRICE $ :"));
        add(price);


        //quantity
        quantity = new Choice();
        add(new Label("Select Quantity:"));
        quantity.setFont(new Font("Calibri",Font.PLAIN,20));
        for(int i=1;i<=15; i++){
            quantity.add(Integer.toString(i));
        }
        add(quantity);

        //Add to cart Button
        addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setFont(new Font("Calibri",Font.BOLD,30));
        add(addToCartBtn);
        addToCartBtn.addActionListener(this);

        //Selected Items List
        selectedItems = new List();
        selectedItems.setFont(new Font("Calibri",Font.BOLD,20));
        add(new Label("Items"));
        selectedItems.addItemListener(this);
        add(selectedItems);

        //Selected Price List
        selectedPrice = new List();
        selectedPrice.setFont(new Font("Calibri",Font.BOLD,20));
        add(new Label("Price"));
        add(selectedPrice);

        //Selected Quantity List
        selectedQuantity = new List();
        selectedQuantity.setFont(new Font("Calibri",Font.BOLD,20));
        add(new Label("Quantity"));
        add(selectedQuantity);

        //total [price & quantity
        total = new List();
        total.setFont(new Font("Calibri",Font.BOLD,20));
        add(new Label("Total"));
        add(total);

        //removeFromCartBtn
        removeFromCartBtn = new Button("Remove Item");
        removeFromCartBtn.addActionListener(this);
        add(removeFromCartBtn);

        //total button
        totalBtn = new Button("TOTAL AMOUNT");
        totalBtn.setFont(new Font("Calibri",Font.BOLD,30));
        totalBtn.addActionListener(this);
        add(totalBtn);


        // full total Amount box
        fullTotalAmt = new TextField(7);
        fullTotalAmt.setFont(new Font("Calibri",Font.BOLD,20));
        add(fullTotalAmt);

        exitBtn = new Button("Exit");
        exitBtn.setFont(new Font("Calibri",Font.BOLD,30));
        exitBtn.addActionListener(this);
        add(exitBtn);
    }
    public static void main(String[] a)
    {
        new bakery();
    }
    public void actionPerformed(ActionEvent t){
        if (t.getSource() == addToCartBtn)
        {
            selectedPrice.setEnabled(false);
            selectedQuantity.setEnabled(false);
            total.setEnabled(false);
            if (items.getSelectedItem() == null)
                System.out.println("Please select an item");
            else{
                selectedItems.add(items.getSelectedItem());
                selectedQuantity.add(quantity.getSelectedItem());
                selectedPrice.add(price.getSelectedItem());
                // calculations
                int pr = Integer.parseInt(price.getSelectedItem());
                int qty = Integer.parseInt(quantity.getSelectedItem());
                total.add(Integer.toString(pr * qty));
                grandTotal += pr * qty;
                quantity.select(0);
            }
        }
        if(t.getSource()== removeFromCartBtn)
        {
            if(selectedItems.getSelectedItem() == null)
                System.out.println("please select the item bro");
            else {
                int pr = Integer.parseInt(selectedPrice.getSelectedItem());
                int qty = Integer.parseInt(selectedQuantity.getSelectedItem());
                selectedItems.remove(selectedItems.getSelectedIndex());
                selectedPrice.remove(selectedPrice.getSelectedIndex());
                selectedQuantity.remove(selectedQuantity.getSelectedIndex());
                total.remove(total.getSelectedIndex());

                grandTotal -= pr * qty;
                fullTotalAmt.setText(Integer.toString(grandTotal));
            }
        }
        if (t.getSource() == totalBtn)
        {
            fullTotalAmt.setText(Integer.toString(grandTotal));
        }
        if(t.getSource() == exitBtn)
        {
            System.exit(0);
        }
    }
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource()== itemList){
            items.removeAll();
            price.removeAll();
            price.setEnabled(false);
            if (itemList.getSelectedItem().equals("Cakes"))
            {
                items.add("Choclate cake"); price.add("50");
                items.add("Vennila cake"); price.add("40");
                items.add("Ice Cake cake"); price.add("80");
                items.add("Pineapple cake"); price.add("60");
                items.add("Strawberry cake"); price.add("60");
            }
            if (itemList.getSelectedItem().equals("Cool Drinks"))
            {
                items.add("Coke-Cola"); price.add("20");
                items.add("Fruit juice"); price.add("30");
                items.add("Sprit"); price.add("30");
                items.add("Lemon juice"); price.add("10");
                items.add("Pepsi"); price.add("20");

            }
            if (itemList.getSelectedItem().equals("Ice Creams"))
            {
                items.add("Chocolate Ice Cream"); price.add("50");
                items.add("Vennila Ice Cream"); price.add("30");
                items.add("ButterSkotch Ice Cream"); price.add("30");
                items.add("Black Currant Ice Cream"); price.add("40");
                items.add("Strawberry Ice Cream"); price.add("30");

            }
            if (itemList.getSelectedItem().equals("Chips"))
            {
                items.add("Potato Chips"); price.add("40");
                items.add("Banana Chips"); price.add("100");
                items.add("Pakoda"); price.add("70");
                items.add("Bundi"); price.add("50");
                items.add("Chilli Chips"); price.add("20");

            }
            if (itemList.getSelectedItem().equals("Other Items"))
            {
                items.add("Vegetable Puffs"); price.add("25");
                items.add("Egg puffs"); price.add("35");
                items.add("Chicken Puffs"); price.add("100");
                items.add("Vegetable Sandwitch"); price.add("30");
                items.add("Murukulu"); price.add("30");

            }
        }
        if (e.getSource()==items)
        {
            price.select(items.getSelectedIndex());
        }
        if(e.getSource()==selectedItems)
        {
            selectedPrice.select(selectedItems.getSelectedIndex());
            selectedQuantity.select(selectedItems.getSelectedIndex());
            total.select(selectedItems.getSelectedIndex());
        }
    }
}

