import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

class Customer {
    String name;
    String email;
    String phoneNumber;
    String address;
    
    public Customer() {}

    public Customer(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    public void setAsDefault() {
        this.name = "John Doe";
        this.email = "john.doe@email.com";
        this.phoneNumber = "0917-123-4567";
        this.address = "42 Wallaby Way, Sydney (Placeholder)";
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
}

class Order {
    ArrayList<Product> cartItems = new ArrayList<>();
    float grandTotal = 0;
    String paymentMethod;
    String paymentDetails;
    String shippingMethod;
    
    public void addToCart(Product product) {
        this.grandTotal += product.getUnitPrice() * product.getQuantity();
        cartItems.add(product);
    }
    
    public ArrayList<Product> getCartList() { return this.cartItems; }
    public float getGrandTotal() { return grandTotal; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentDetails() { return paymentDetails; }
    public String getShippingMethod() { return shippingMethod; }
    
    public void setPaymentDetails(String details) { this.paymentDetails = details; }
    
    public void setPaymentMethod(int choice) {
        switch (choice) {
            case 1: this.paymentMethod = "Cash On Delivery (COD)"; this.paymentDetails = "N/A"; break;
            case 2: this.paymentMethod = "Card (Visa/Mastercard)"; break;
            case 3: this.paymentMethod = "QR Code (GCash/Maya)"; break;
            default: this.paymentMethod = "Unknown"; break;
        }
    }
    
    public void setShippingMethod(int choice) {
        switch (choice) {
            case 1: this.shippingMethod = "Door to Door Delivery"; break;
            case 2: this.shippingMethod = "Store Pick-up"; break;
            default: this.shippingMethod = "Standard"; break;
        }
    }
}

class Product {
    static int ctr = 1;
    int id;
    String productName;
    String category;
    String brand;
    float unitPrice;
    int quantity;
    
    public Product() {}
    
    public Product(String brand, String productName, String category, float unitPrice, int quantity) {
        this.id = ctr++;
        this.brand = brand;
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Product(Product p, int orderQty) {
        this.id = p.getId();
        this.brand = p.getBrand();
        this.productName = p.getProductName();
        this.category = p.getCategory();
        this.unitPrice = p.getUnitPrice();
        this.quantity = orderQty;
    }
    
    public int getId() { return id; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public String getBrand() { return brand; }
    public float getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnitPrice(float unitPrice) { this.unitPrice = unitPrice; }
}

class Inventory {
    ArrayList<Product> products = new ArrayList<>();
    
    public void addProduct(Product product) {
        this.products.add(product);
    }
    
    public ArrayList<Product> getInventory() {
        return this.products;
    }
    
    public Product getItem(int id) {
        for (Product item: products) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Product p : products) {
            if (!categories.contains(p.getCategory())) {
                categories.add(p.getCategory());
            }
        }
        return categories;
    }
}

class Main {
    private static Inventory inventory = new Inventory();
    private static Order order = new Order();
    private static Customer customer = new Customer();
    
    private static final List<String> AUTHORS = List.of(
        "John Mark Baptista",
        "Samuel Odono",
        "Brian Mikko Aravilla",
        "Joshua Villaruel"
    );

    static void InitializeProductList() {
        
        
        inventory.addProduct(new Product("Sony", "PlayStation 5 Pro", "Consoles", 699.99f, 10));
        inventory.addProduct(new Product("Sony", "PlayStation 5 Slim (Disc)", "Consoles", 499.99f, 25));
        
        inventory.addProduct(new Product("Microsoft", "Xbox Series X", "Consoles", 499.99f, 15));
        inventory.addProduct(new Product("Microsoft", "Xbox Series S", "Consoles", 299.99f, 30));
        
        inventory.addProduct(new Product("Nintendo", "Switch OLED Model", "Consoles", 349.99f, 40));
        inventory.addProduct(new Product("Nintendo", "Switch Lite", "Consoles", 199.99f, 50));
        
        inventory.addProduct(new Product("Valve", "Steam Deck OLED (512GB)", "Consoles", 549.00f, 8));
        inventory.addProduct(new Product("Valve", "Steam Deck LCD (256GB)", "Consoles", 399.00f, 12));
        
        inventory.addProduct(new Product("Asus", "ROG Ally X", "Consoles", 799.99f, 5));

        
        inventory.addProduct(new Product("NVIDIA", "GeForce RTX 4090", "PC Parts", 1599.00f, 3));
        inventory.addProduct(new Product("NVIDIA", "GeForce RTX 4070 Super", "PC Parts", 599.00f, 10));
        inventory.addProduct(new Product("AMD", "Radeon RX 7900 XTX", "PC Parts", 999.00f, 5));
        inventory.addProduct(new Product("AMD", "Ryzen 7 7800X3D CPU", "PC Parts", 399.00f, 20));
        inventory.addProduct(new Product("Intel", "Core i9-14900K CPU", "PC Parts", 549.00f, 15));
        inventory.addProduct(new Product("Corsair", "Vengeance 32GB DDR5 RAM", "PC Parts", 129.99f, 40));
        inventory.addProduct(new Product("Samsung", "990 PRO 2TB NVMe SSD", "PC Parts", 169.99f, 35));

        
        inventory.addProduct(new Product("Logitech", "G Pro X Superlight 2", "Mouse", 159.00f, 20));
        inventory.addProduct(new Product("Razer", "DeathAdder V3 Pro", "Mouse", 149.00f, 20));
        inventory.addProduct(new Product("Razer", "Huntsman V3 Pro Keyboard", "Keyboard", 249.00f, 10));
        inventory.addProduct(new Product("Keychron", "Keychron Q1 Max", "Keyboard", 219.00f, 8));
        inventory.addProduct(new Product("Wooting", "Wooting 60HE+", "Keyboard", 175.00f, 5));

        
        inventory.addProduct(new Product("Alienware", "AW3423DWF OLED Monitor", "Monitors", 899.00f, 5));
        inventory.addProduct(new Product("LG", "UltraGear 27GR95QE OLED", "Monitors", 799.00f, 7));
        inventory.addProduct(new Product("Samsung", "Odyssey G9 OLED", "Monitors", 1199.00f, 4));
        inventory.addProduct(new Product("BenQ", "Zowie XL2566K 360Hz", "Monitors", 599.00f, 10));

        
        inventory.addProduct(new Product("FromSoft", "Elden Ring: SotE Edition", "Games", 79.99f, 50));
        inventory.addProduct(new Product("Activision", "Call of Duty: Black Ops 6", "Games", 69.99f, 100));
        inventory.addProduct(new Product("Rockstar", "GTA V (Next Gen)", "Games", 29.99f, 200));
        inventory.addProduct(new Product("Nintendo", "Super Mario Wonder", "Games", 59.99f, 60));
        inventory.addProduct(new Product("Sony", "Spider-Man 2", "Games", 69.99f, 45));
        inventory.addProduct(new Product("Capcom", "Monster Hunter Wilds (Pre-order)", "Games", 69.99f, 100));
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    static void renderHeader(String title) {
        System.out.println("===============================================================");
        System.out.println("  " + title);
        System.out.println("===============================================================");
    }

    static void displayAuthors(Scanner scanner) {
        clearScreen();
        renderHeader("Application Authors");
        System.out.println("\tContributors to GameSpot Retail System v2.0:\n");
        for (String author : AUTHORS) {
            System.out.println("\t- " + author);
        }
        System.out.println("\n\tPress Enter to return to menu...");
        try { scanner.nextLine(); System.in.read(); } catch(Exception e){}
    }

    static void displayCategories() {
        ArrayList<String> cats = inventory.getCategories();
        System.out.println("\n\tWhat are you looking for?");
        System.out.println("\t0. Finish Shopping / Back to Main Menu");
        for (int i = 0; i < cats.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + cats.get(i));
        }
        System.out.println("\t" + (cats.size() + 1) + ". View All Products");
    }

    static void displayProducts(ArrayList<Product> products) {
        System.out.printf("\n\t%-4s %-12s %-30s %-12s %-5s%n", "ID", "Brand", "Name", "Price", "Stock");
        System.out.println("\t---------------------------------------------------------------------");
        for (Product item: products) {
            System.out.printf("\t%-4d %-12s %-30s $%-11.2f %-5d%n", 
                item.getId(), 
                item.getBrand(),
                (item.getProductName().length() > 28 ? item.getProductName().substring(0,25)+"..." : item.getProductName()), 
                item.getUnitPrice(), 
                item.getQuantity());
        }
    }

    static void addItemToCart(Scanner scanner) {
        char moreChoice = 'n';
        do {
            clearScreen();
            renderHeader("Store Inventory");
            
            displayCategories();
            ArrayList<String> cats = inventory.getCategories();
            System.out.print("\n\tSelect Category: ");
            int catChoice = -1;
            try { catChoice = scanner.nextInt(); } catch(Exception e) { scanner.next(); }

            if (catChoice == 0) return;

            ArrayList<Product> displayList;
            if (catChoice > 0 && catChoice <= cats.size()) {
                String selectedCat = cats.get(catChoice - 1);
                displayList = inventory.getProductsByCategory(selectedCat);
                System.out.println("\n\t--- Browsing: " + selectedCat + " ---");
            } else {
                displayList = inventory.getInventory();
                System.out.println("\n\t--- Browsing: All Products ---");
            }

            displayProducts(displayList);

            System.out.print("\n\tEnter Product ID to add (0 to cancel): ");
            int prodChoice = scanner.nextInt();
            
            if (prodChoice == 0) {
                moreChoice = 'y'; 
                continue;
            }

            Product stockItem = inventory.getItem(prodChoice);
            
            if (stockItem != null) {
                System.out.print("\tEnter Quantity (Available: " + stockItem.getQuantity() + "): ");
                int qtyChoice = scanner.nextInt();
                
                if (qtyChoice <= 0 || qtyChoice > stockItem.getQuantity()) {
                    System.out.println("\t>> Error: Invalid quantity or not enough stock!");
                } else {
                    Product cartItem = new Product(stockItem, qtyChoice);
                    order.addToCart(cartItem);
                    stockItem.setQuantity(stockItem.getQuantity() - qtyChoice);
                    System.out.println("\t>> Added " + qtyChoice + "x " + stockItem.getProductName() + " to cart.");
                }
            } else {
                System.out.println("\t>> Product not found.");
            }
            
            System.out.println("\tCurrent Cart Total: $" + String.format("%.2f", order.getGrandTotal()));

            System.out.print("\n\tKeep Shopping? (y/n): ");
            moreChoice = scanner.next().charAt(0);
            
        } while (moreChoice == 'y' || moreChoice == 'Y');
    }

    static boolean inputCustomerInformation(Scanner scanner) {
        scanner.nextLine(); 
        clearScreen();
        renderHeader("Checkout - Customer Details");
        
        System.out.print("\t[Quick Fill] Use 'John Doe' placeholder info? (y/n): ");
        String useDefault = scanner.nextLine();
        
        if (useDefault.equalsIgnoreCase("y")) {
            customer.setAsDefault();
        } else {
            System.out.print("\tEnter Name: ");
            customer.setName(scanner.nextLine());
            System.out.print("\tEnter Email: ");
            customer.setEmail(scanner.nextLine());
            System.out.print("\tEnter Phone Number: ");
            customer.setPhoneNumber(scanner.nextLine());
            System.out.print("\tEnter Address: ");
            customer.setAddress(scanner.nextLine());
        }

        System.out.println("\n\t--- Shipping Method ---");
        System.out.println("\t0. Cancel Checkout");
        System.out.println("\t1. Door to Door Delivery (+$0.00)");
        System.out.println("\t2. Store Pick-up (Free)");
        System.out.print("\tChoice: ");
        int shipChoice = 1; 
        try { shipChoice = scanner.nextInt(); } catch(Exception e){ scanner.next(); }
        
        if (shipChoice == 0) return false;

        order.setShippingMethod(shipChoice);

        System.out.println("\n\t--- Payment Method ---");
        System.out.println("\t0. Cancel Checkout");
        System.out.println("\t1. Cash on Delivery (COD)");
        System.out.println("\t2. Card (Visa/Mastercard)");
        System.out.println("\t3. QR Code (GCash/Maya)");
        System.out.print("\tChoice: ");
        
        int payChoice = 1;
        try { payChoice = scanner.nextInt(); } catch(Exception e){ scanner.next(); }
        
        if (payChoice == 0) return false;

        order.setPaymentMethod(payChoice);
        scanner.nextLine(); 
        
        if (payChoice == 2) {
            System.out.print("\t>> Enter Card Number (xxxx-xxxx-xxxx-xxxx): ");
            order.setPaymentDetails(scanner.nextLine());
        } else if (payChoice == 3) {
            System.out.print("\t>> Enter Mobile Number: ");
            order.setPaymentDetails(scanner.nextLine());
        }

        return true;
    }
    
    static char displayConfirmation(Scanner scanner) {
        clearScreen();
        renderHeader("Order Confirmation");
        
        System.out.println("\tCustomer: " + customer.getName());
        System.out.println("\tContact:  " + customer.getEmail() + " | " + customer.getPhoneNumber());
        System.out.println("\tShip To:  " + customer.getAddress());
        System.out.println("\n\tPayment:  " + order.getPaymentMethod());
        System.out.println("\tShipping: " + order.getShippingMethod());
        
        System.out.println("\n\t--- Items Ordered ---");
        for(Product p : order.getCartList()) {
            System.out.printf("\t%-30s x%d  $%.2f%n", p.getProductName(), p.getQuantity(), (p.getUnitPrice() * p.getQuantity()));
        }
        System.out.println("\t-------------------------------------");
        System.out.printf("\tTOTAL AMOUNT DUE:             $%.2f%n", order.getGrandTotal());

        System.out.print("\n\tConfirm Order? (y/n): ");
        char confirm = scanner.next().charAt(0);

        if (confirm == 'y' || confirm == 'Y') {
            System.out.print("\n\tContacting Bank...");
            try { Thread.sleep(800); System.out.print("."); Thread.sleep(800); } catch(Exception e){}
            System.out.println("\n\tSuccess! Thank you for shopping at GameSpot.");
        } else {
            System.out.println("\n\tOrder Cancelled.");
            restoreStock();
        }
        
        System.out.println("\n\tPress Enter to return to menu...");
        try { System.in.read(); } catch(Exception e){}
        
        return confirm;
    }
    
    static void restoreStock() {
        for(Product p : order.getCartList()) {
            Product stockItem = inventory.getItem(p.getId());
            if(stockItem != null) {
                stockItem.setQuantity(stockItem.getQuantity() + p.getQuantity());
            }
        }
    }

    static void managerMenu(Scanner scanner) {
        int mChoice = 0;
        do {
            clearScreen();
            renderHeader("Manager Dashboard");
            System.out.println("\t0. Return to Main Menu");
            System.out.println("\t1. View Stock (All)");
            System.out.println("\t2. Restock Item (Add Qty)");
            System.out.println("\t3. Reprice Item");
            System.out.print("\n\tAction: ");
            try { mChoice = scanner.nextInt(); } catch(Exception e) { mChoice=-1; scanner.next(); }
            
            if (mChoice == 0) return;

            if (mChoice == 1) {
                displayProducts(inventory.getInventory());
                System.out.println("\n\tPress Enter...");
                try { System.in.read(); } catch(Exception e){}
            }
            else if (mChoice == 2 || mChoice == 3) {
                System.out.print("\tEnter Product ID: ");
                int id = scanner.nextInt();
                Product p = inventory.getItem(id);
                if (p != null) {
                    System.out.println("\tSelected: " + p.getProductName());
                    if (mChoice == 2) {
                        System.out.print("\tCurrent Stock: " + p.getQuantity() + ". Enter NEW TOTAL: ");
                        p.setQuantity(scanner.nextInt());
                        System.out.println("\t>> Updated.");
                    } else {
                        System.out.print("\tCurrent Price: $" + p.getUnitPrice() + ". Enter NEW Price: ");
                        p.setUnitPrice(scanner.nextFloat());
                        System.out.println("\t>> Updated.");
                    }
                } else {
                    System.out.println("\t>> Invalid ID.");
                }
            }
        } while (true);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InitializeProductList();
        int choice = -1;
        
        do {
            clearScreen();
            renderHeader("GameSpot Retail System (v2.0)");
            System.out.println("\t0. Exit");
            System.out.println("\t1. Shop (Browse & Buy)");
            System.out.println("\t2. Manager Login");
            System.out.println("\t3. View Authors");
            System.out.print("\n\tEnter Choice: ");
            
            try { choice = scanner.nextInt(); } catch(Exception e) { choice=-1; scanner.next(); }

            switch(choice) {
                case 0: 
                    System.out.println("\tGoodbye!");
                    return;
                case 1:
                    order = new Order();
                    customer = new Customer();
                    addItemToCart(scanner);
                    if (!order.getCartList().isEmpty()) {
                        if(inputCustomerInformation(scanner)) {
                            displayConfirmation(scanner);
                        } else {
                            System.out.println("\n\t>> Checkout Cancelled. Returning items to shelf...");
                            restoreStock();
                            try { Thread.sleep(1500); } catch(Exception e){}
                        }
                    }
                    break;
                case 2:
                    managerMenu(scanner);
                    break;
                case 3:
                    displayAuthors(scanner);
                    break;
            }
        } while (choice != 0);
        scanner.close();
    }
}