<?php
$servername = "sql212.infinityfree.com";
$username = "if0_41413271";
$password = "pd43au2hVla3T";
$dbname = "if0_41413271_manding_system";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) { die("Connection failed: " . $conn->connect_error); }
$conn->set_charset("utf8");
?>