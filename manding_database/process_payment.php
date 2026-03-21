<?php
session_start();
include 'db_config.php';

if (!isset($_SESSION['temp_order'])) {
    header("Location: shop.php");
    exit();
}

$order = $_SESSION['temp_order'];
$item_id = $order['item_id'];
$qty = $order['qty'];
$total = $order['total'];
$branch = $order['branch'];
$branch_col = $order['branch_col'];

if (isset($_POST['confirm_payment'])) {
    $update_sql = "UPDATE inventory SET $branch_col = $branch_col - $qty WHERE id = $item_id";
    
    $sales_sql = "INSERT INTO sales (item_id, quantity_sold, total_price, payment_method, branch_location) 
                  VALUES ($item_id, $qty, $total, 'GCash', '$branch')";
    
    if ($conn->query($update_sql) && $conn->query($sales_sql)) {
        unset($_SESSION['temp_order']);
        echo "<script>alert('GCash Payment Successful! Your order is on the way from $branch.'); window.location.href='shop.php';</script>";
        exit();
    } else {
        echo "Error: " . $conn->error;
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>GCash Payment Confirmation</title>
    <style>
        body { font-family: sans-serif; background: #f0f2f5; display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .back-btn { text-decoration: none; color: #606c38; border: 2px solid #606c38; padding: 8px 20px; border-radius: 25px; background: white; font-weight: bold; margin-bottom: 20px; }
        .payment-card { background: white; padding: 40px; border-radius: 20px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); text-align: center; width: 360px; }
        .method-title { color: #007bff; font-size: 28px; font-weight: bold; margin-bottom: 5px; }
        .branch-tag { color: #606c38; font-weight: bold; margin-bottom: 20px; display: block; }
        .amount { font-size: 18px; margin-bottom: 25px; }
        .phone-input { width: 100%; padding: 15px; margin-bottom: 20px; border: 2px solid #333; border-radius: 12px; font-size: 18px; text-align: center; box-sizing: border-box; }
        .pay-btn { background: #007bff; color: white; border: none; padding: 16px; width: 100%; border-radius: 12px; font-weight: bold; font-size: 16px; cursor: pointer; }
    </style>
</head>
<body>
    <a href="shop.php" class="back-btn">← Cancel Payment</a>

    <div class="payment-card">
        <div class="method-title">GCash</div>
        <span class="branch-tag">Branch: <?php echo $branch; ?></span>
        <div class="amount">Total Amount: <strong>₱<?php echo number_format($total, 2); ?></strong></div>
        
        <form method="POST">
            <input type="text" class="phone-input" placeholder="09XX XXX XXXX" maxlength="11" required>
            <button type="submit" name="confirm_payment" class="pay-btn">Confirm GCash Pay</button>
        </form>
    </div>
</body>
</html>