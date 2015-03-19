<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["getClientsJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	$res = $db->getAllClientsOfCompany($data[0]->company_id);
	if ($res != false) {
		$no_of_clients = mysql_num_rows($res);
		$b["works"] = 'yes';
		$b["nb_of_clients"] = (string)$no_of_clients;
	} else {
		$no_of_clients = 0;
		$b["works"] = 'no';
	}
	
	for ($i = 0 ; $i < $no_of_clients ; $i++) {
		$row = mysql_fetch_array($res);
		$b["client{$i}_name"] = $row["name"];
		$b["client{$i}_first_name"] = $row["first_name"];
		$b["client{$i}_username"] = $row["username"];
		$b["client{$i}_num_client"] = $row["num_client"];
		$b["client{$i}_nb_points"] = $row["nb_loyalties"];
		$b["client{$i}_last_used"] = $row["last_used"];
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>