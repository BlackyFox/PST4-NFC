<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["getOffersJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	$res = $db->getAllOffersOf($data[0]->company_id);
	if ($res != false) {
		$no_of_offers = mysql_num_rows($res);
		$b["has_offers"] = 'yes';
		$b["nb_of_offers"] = (string)$no_of_offers;
	} else {
		$no_of_offers = 0;
		$b["has_offers"] = 'no';
		$b["nb_of_offers"] = (string)$no_of_offers;
	}
	
	for ($i = 0 ; $i < $no_of_offers ; $i++) {
		$row = mysql_fetch_array($res);
		$b["offer{$i}_id"] =  $row["id"];
		$b["offer{$i}_redu_name"] =  $row["name"];
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>