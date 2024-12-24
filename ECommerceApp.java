import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product [ID: " + id + ", Name: " + name + ", Price: $" + price + "]";
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " (x" + quantity + ") - Total: $" + getTotalPrice();
    }
}

class ShoppingCart {
    private List<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    public void removeProduct(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your Cart:");
            for (CartItem item : cartItems) {
                System.out.println(item);
            }
        }
    }

    public double getTotalAmount() {
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}

public class ECommerceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = Arrays.asList(
            new Product(1, "Laptop", 1200.00),
            new Product(2, "Smartphone", 800.00),
            new Product(3, "Headphones", 150.00),
            new Product(4, "Keyboard", 100.00)
        );

        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("\n=== E-Commerce System ===");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Products:");
                    for (Product product : products) {
                        System.out.println(product);
                    }
                    break;
                case 2:
                    System.out.print("Enter Product ID to add: ");
                    int addId = scanner.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    Optional<Product> productToAdd = products.stream().filter(p -> p.getId() == addId).findFirst();
                    if (productToAdd.isPresent()) {
                        cart.addProduct(productToAdd.get(), quantity);
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("Invalid Product ID.");
                    }
                    break;
                case 3:
                    cart.viewCart();
                    break;
                case 4:
                    System.out.print("Enter Product ID to remove: ");
                    int removeId = scanner.nextInt();
                    cart.removeProduct(removeId);
                    System.out.println("Product removed from cart.");
                    break;
                case 5:
                    System.out.println("\n=== Checkout ===");
                    cart.viewCart();
                    System.out.println("Total Amount: $" + cart.getTotalAmount());
                    System.out.println("Thank you for your purchase!");
                    cart.clearCart();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you for visiting!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
