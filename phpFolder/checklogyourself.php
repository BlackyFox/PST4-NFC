<?php
	include_once './rsa.php';
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$rsa = new RSA('public.pem','private.pem','123456');
	$json = $_POST["logJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	//$b["msg1"] = "coucou";
	//$b["msg1Encry"] = $rsa->encrypt($b["msg1"]);
	//$b["msg1Decry"] = $rsa->decrypt($b["msg1Encry"]);
	
	$result = $db->checkLog($data[0]->username, $data[0]->password);
	if(mysql_num_rows($result) > 0) {
		$b["log"] = 'yes';
		
		$people = mysql_fetch_array($result);
		$b["id"] = $people["id"];
		$b["username"] = $people["username"];
		$b["password"] = $people["password"];
		$b["name"] = $people["name"];
		$b["first_name"] = $people["first_name"];
		$b["sexe"] = $people["sexe"];
		$b["date_of_birth"] = $people["date_of_birth"];
		$b["mail"] = $people["mail"];
		$b["city"] = $people["city"];
		$b["up_date"] = $people["up_date"];

		//Update current clients
		$res = $db->getAllClientsJoined($b["id"]);
		if ($res != false) {
			$no_of_clients = mysql_num_rows($res);
			$b["has_clients"] = 'yes';
			$b["has_clients_number"] = (string)$no_of_clients;
		} else {
			$no_of_clients = 0;
			$b["has_clients"] = 'no';
		}
		
		for ($i = 0 ; $i < $no_of_clients ; $i++) {
			$row = mysql_fetch_array($res);
			$b["client{$i}_id"] =  $row["id"];
			$b["client{$i}_new_id_peop"] =  $row["id_peop"];
			$b["client{$i}_new_id_comp"] =  $row["id_comp"];
			$b["client{$i}_new_num_client"] =  $row["num_client"];
			$b["client{$i}_new_nb_loyalties"] =  $row["nb_loyalties"];
			$b["client{$i}_new_last_used"] =  $row["last_used"];
			$b["client{$i}_new_up_date"] =  $row["up_date"];
			
			$res2 = $db->getCompanyWithId($row["id_comp"]);
			$row2 = mysql_fetch_array($res2);
			$b["client{$i}_company_id"] =  $row2["id"];
			$b["client{$i}_company_name"] =  $row2["name"];
			$b["client{$i}_company_logo"] =  $row2["logo"];
			$b["client{$i}_company_card"] =  $row2["card"];
			$b["client{$i}_company_up_date"] =  $row2["up_date"];
			
					
			$res3 = $db->getAllOffersProposedByCompany($row2["id"]);
			if ($res3 != false) {
				$no_of_offers = mysql_num_rows($res3);
				$b["client{$i}_has_offers"] = 'yes';
				$b["client{$i}_has_offers_number"] = (string)$no_of_offers;
			} else {
				$no_of_offers = 0;
				$b["client{$i}_has_offers"] = 'no';
			}
		
			for ($j = 0 ; $j < $no_of_offers ; $j++) {
				$row3 = mysql_fetch_array($res3);
				$b["client{$i}_offer{$j}_id"] =  $row3["id"];
				$b["client{$i}_offer{$j}_id_comp"] =  $row3["id_comp"];
				$b["client{$i}_offer{$j}_id_redu"] =  $row3["id_redu"];
				$b["client{$i}_offer{$j}_up_date"] =  $row3["up_date"];
				
				$res4 = $db->getReductionWithId($row3["id_redu"]);
				$row4 = mysql_fetch_array($res4);
				$b["client{$i}_offer{$j}_reduction_id"] =  $row4["id"];
				$b["client{$i}_offer{$j}_reduction_name"] =  $row4["name"];
				$b["client{$i}_offer{$j}_reduction_description"] =  $row4["description"];
				$b["client{$i}_offer{$j}_reduction_sexe"] =  $row4["sexe"];
				$b["client{$i}_offer{$j}_reduction_age_relation"] =  $row4["age_relation"];
				$b["client{$i}_offer{$j}_reduction_age_value"] =  $row4["age_value"];
				$b["client{$i}_offer{$j}_reduction_nb_points_relation"] =  $row4["nb_points_relation"];
				$b["client{$i}_offer{$j}_reduction_nb_points_value"] =  $row4["nb_points_value"];
				$b["client{$i}_offer{$j}_reduction_city"] =  $row4["city"];
				$b["client{$i}_offer{$j}_reduction_up_date"] =  $row4["up_date"];
			}
		}
	} else {
		$b["log"] = 'no';
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>