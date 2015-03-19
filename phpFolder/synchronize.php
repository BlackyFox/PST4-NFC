<?php
	include_once './db_functions.php';
	
	$db = new DB_Functions();
	$json = $_POST["synchronizeJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();

	//Update current people
	$res = $db->isPeopleUpdated($data[0]->current_people_id);
	$row = mysql_fetch_array($res);
	
	if($row == null) {
		$b["update_people_works"] = 'no';
	} else {
		$b["update_people_works"] = 'yes';
		if($row["up_date"] == $data[0]->current_people_up_date) {
			$b["has_to_update_people"] = 'no';
		} else {
			$b["has_to_update_people"] = 'yes';
			$b["people_new_username"] =  $row["username"];
			$b["people_new_password"] =  $row["password"];
			$b["people_new_name"] =  $row["name"];
			$b["people_new_first_name"] =  $row["first_name"];
			$b["people_new_sexe"] =  $row["sexe"];
			$b["people_new_date_of_birth"] =  $row["date_of_birth"];
			$b["people_new_mail"] =  $row["mail"];
			$b["people_new_city"] =  $row["city"];
			$b["people_new_up_date"] =  $row["up_date"];
		}
	}

	//Update current clients
	$res = $db->getAllClientsJoined($data[0]->current_people_id);
	if ($res != false) {
		$no_of_clients = mysql_num_rows($res);
		$b["has_clients"] = 'yes';
		$b["debug"] = (string)$data[0]->current_people_id;
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
	
	array_push($a,$b);
	echo json_encode($a);
?>