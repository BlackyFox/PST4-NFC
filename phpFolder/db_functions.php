<?php
	class DB_Functions {
		private $db;
		
		function __construct() {
			include_once './db_connect.php';
			$this->db = new DB_Connect();
			$this->db->connect();
		}
		
		function __destruct() { }
		
		function jsonRemoveUnicodeSequences($struct) {
			return preg_replace("/\\\\u([a-f0-9]{4})/e", "iconv('UCS-4LE','UTF-8',pack('V', hexdec('U$1')))", json_encode($struct));
		}
		
		public function insertPeople($username, $password, $name, $first_name, $sexe, $date_of_birth, $mail, $city, $up_date) {
			$result = mysql_query("INSERT INTO people (username, password, name, first_name, sexe, date_of_birth, mail, city, up_date)
							VALUES ('$username', '$password', '$name', '$first_name', '$sexe', '$date_of_birth', '$mail', '$city', '$up_date')");
			
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
		
		public function insertClient($id_peop, $id_comp, $num_client, $nb_loyalties, $last_used, $joined, $up_date) {
			$result = mysql_query("INSERT INTO clients (id_peop, id_comp, num_client, nb_loyalties, last_used, joined, up_date)
							VALUES ('$id_peop', '$id_comp', '$num_client', '$nb_loyalties', '$last_used', '$joined', '$up_date')");
			
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
		
		public function insertReduction($name, $description, $sexe, $age_relation, $age_value, $nb_points_relation, $nb_points_value, $city, $up_date) {
			$result = mysql_query("INSERT INTO reductions (name, description, sexe, age_relation, age_value, nb_points_relation, nb_points_value, city, up_date)
							VALUES ('$name', '$description', '$sexe', '$age_relation', '$age_value', '$nb_points_relation', '$nb_points_value', '$city', '$up_date')");
			
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
		
		public function insertOffer($id_comp, $id_redu, $up_date) {
			$result = mysql_query("INSERT INTO offers (id_comp, id_redu, up_date)
							VALUES ('$id_comp', '$id_redu', '$up_date')");
			
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
		
		public function getAllPeople() {
			$result = mysql_query("SELECT * FROM people");
			return $result;
		}
		
		public function getAllCompanies() {
			$result = mysql_query("SELECT * FROM companies");
			return $result;
		}
		
		public function getAllClients() {
			$result = mysql_query("SELECT * FROM clients");
			return $result;
		}
		
		public function getAllClientsJoined($id_peop) {
			$result = mysql_query("SELECT * FROM clients WHERE id_peop = '$id_peop' AND joined = 1");
			return $result;
		}
		
		public function getAllClientsOfCompany($id_comp) {
			$result = mysql_query("SELECT p.name, p.first_name, p.username, c.num_client, c.nb_loyalties, c.last_used FROM people p, clients c WHERE c.id_peop = p.id AND c.id_comp = '$id_comp'");
			return $result;
		}
		
		public function getAllReductions() {
			$result = mysql_query("SELECT * FROM reductions");
			return $result;
		}
		
		public function getAllOffers() {
			$result = mysql_query("SELECT * FROM offers");
			return $result;
		}
		
		public function getAllOffersProposedByCompany($id) {
			$result = mysql_query("SELECT * FROM offers WHERE id_comp = '$id'");
			return $result;
		}
		
		public function getAllOffersOf($id) {
			$result = mysql_query("SELECT o.id, r.name FROM offers o, reductions r WHERE o.id_comp = '$id' AND o.id_redu = r.id");
			return $result;
		}
		
		public function getAllOpportunities() {
			$result = mysql_query("SELECT * FROM opportunities");
			return $result;
		}
		
		public function doesPeopleAlreadyExists($username) {
			$result = mysql_query("SELECT * FROM people WHERE username = '$username'");
			if(mysql_num_rows($result) > 0) { return true; }
			else { return false; }
		}
		
		public function doesClientAlreadyExists($id_peop, $id_comp) {
			$result = mysql_query("SELECT * FROM clients WHERE id_peop = '$id_peop' AND id_comp = '$id_comp'");
			if(mysql_num_rows($result) > 0) { return true; }
			else { return false; }
		}
		
		public function doesReductionAlreadyExists($name) {
			$result = mysql_query("SELECT * FROM reductions WHERE name = '$name'");
			if(mysql_num_rows($result) > 0) { return true; }
			else { return false; }
		}
		
		public function doesOfferAlreadyExists($id_comp, $id_redu) {
			$result = mysql_query("SELECT * FROM offers WHERE id_comp = '$id_comp' AND id_redu = '$id_redu'");
			if(mysql_num_rows($result) > 0) { return true; }
			else { return false; }
		}
		
		public function updateClient($id_peop, $id_client) {
			$res1 = mysql_query("SELECT * FROM clients WHERE id_peop = '$id_peop' AND last_used = 1");
			$res2 = mysql_query("SELECT * FROM clients WHERE id_peop = '$id_peop' AND last_used = 2");
			$res3 = mysql_query("SELECT * FROM clients WHERE id_peop = '$id_peop' AND last_used = 3");
			if(mysql_num_rows($res1) == 0) {
				$last = 1;
			} else if(mysql_num_rows($res2) == 0) {
				$last = 2;
			} else if(mysql_num_rows($res3) == 0) {
				$last = 3;
			} else {
				$last = 0;
			}
			
			$result = mysql_query("UPDATE clients SET last_used = '$last', joined = 1 WHERE id = '$id_client'");
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
		
		public function updateClient2($id_peop, $id_comp, $n_c, $r_n) {
			$res4 = mysql_query("SELECT * FROM clients WHERE id_peop = '$id_peop' AND id_comp = '$id_comp'");
			$row = mysql_fetch_array($res4);
			$id = $row["id"];
			if($id < 10) {
				$id_str = "0000{$id}";
			} else if($id < 100) {
				$id_str = "000{$id}";
			} else if($id < 1000) {
				$id_str = "00{$id}";
			} else if($id < 10000) {
				$id_str = "0{$id}";
			} else {
				$id_str = (string)$id;
			}
			
			$num_client = "{$id_str}{$n_c}{$r_n}";
			
			mysql_query("UPDATE clients SET num_client = '$num_client' WHERE id = '$id'");
		}
		
		public function addOneLoyalty($num_client, $up_date) {
			$res = mysql_query("SELECT * FROM clients WHERE num_client = '$num_client'");
			$row = mysql_fetch_array($res);
			$id = $row["id"];
			$newNb = $row["nb_loyalties"] + 1;
			
			$idpeop = $row["id_peop"];
			if($row["last_used"] == 2) {
				$res1 = mysql_query("SELECT * FROM clients WHERE id_peop = '$idpeop' AND last_used = 1");
				$row1 = mysql_fetch_array($res1);
				$tmpId1 = $row1["id"];
				mysql_query("UPDATE clients SET last_used = 2, up_date = '$up_date' WHERE id = '$tmpId1'");
			} else if($row["last_used"] == 3) {
				$res1 = mysql_query("SELECT * FROM clients WHERE id_peop = '$idpeop' AND last_used = 1");
				$res2 = mysql_query("SELECT * FROM clients WHERE id_peop = '$idpeop' AND last_used = 2");
				$row1 = mysql_fetch_array($res1);
				$row2 = mysql_fetch_array($res2);
				$tmpId1 = $row1["id"];
				$tmpId2 = $row2["id"];
				mysql_query("UPDATE clients SET last_used = 2, up_date = '$up_date' WHERE id = '$tmpId1'");
				mysql_query("UPDATE clients SET last_used = 3, up_date = '$up_date' WHERE id = '$tmpId2'");
			} else if($row["last_used"] == 0) {
				$res1 = mysql_query("SELECT * FROM clients WHERE id_peop = '$idpeop' AND last_used = 1");
				$res2 = mysql_query("SELECT * FROM clients WHERE id_peop = '$idpeop' AND last_used = 2");
				$res3 = mysql_query("SELECT * FROM clients WHERE id_peop = '$idpeop' AND last_used = 3");
				$row1 = mysql_fetch_array($res1);
				$row2 = mysql_fetch_array($res2);
				$row3 = mysql_fetch_array($res3);
				$tmpId1 = $row1["id"];
				$tmpId2 = $row2["id"];
				$tmpId3 = $row3["id"];
				mysql_query("UPDATE clients SET last_used = 2, up_date = '$up_date' WHERE id = '$tmpId1'");
				mysql_query("UPDATE clients SET last_used = 3, up_date = '$up_date' WHERE id = '$tmpId2'");
				mysql_query("UPDATE clients SET last_used = 0, up_date = '$up_date' WHERE id = '$tmpId3'");
			}
			
			$result = mysql_query("UPDATE clients SET nb_loyalties = '$newNb', last_used = 1, up_date = '$up_date' WHERE id = '$id'");
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}

		public function checkIfClientIsOk($num_client, $name, $first_name, $date_of_birth, $sexe) {
			$result = mysql_query("SELECT * FROM people WHERE id IN (SELECT id_peop FROM clients WHERE num_client = '$num_client')");
			$row = mysql_fetch_array($result);
			if($row == null) { return false; }
			else if($row["name"] == $name && $row["first_name"] == $first_name && $row["sexe"] == $sexe && $row["date_of_birth"] == $date_of_birth) { return true; }
			else { return false; }
		}

		public function checkIfClientIsOkWithNumClient($num_client) {
			$result = mysql_query("SELECT * FROM clients WHERE num_client = '$num_client'");
			$row = mysql_fetch_array($result);
			if($row == null) { return false; }
			else { return true; }
		}

		public function checkIfClientAlreadyExistsWithNumClient($num_client) {
			$result = mysql_query("SELECT * FROM clients WHERE num_client = '$num_client'");
			$row = mysql_fetch_array($result);
			if($row == null) { return false; }
			else { return true; }
		}
		
		public function checkIfTruePeople($id, $num_client) {
			$result = mysql_query("SELECT * FROM clients WHERE num_client = '$num_client'");
			$row = mysql_fetch_array($result);
			if(intval($row["id_peop"]) == intval($id)) { return true; }
			else { return false; }
		}
		
		public function checkIfOfferAlreadyExistsWithId($id) {
			$result = mysql_query("SELECT * FROM offers WHERE id = '$id'");
			$row = mysql_fetch_array($result);
			if($row == null) { return false; }
			else { return true; }
		}

		public function getClientWithNumClient($num_client) {
			$result = mysql_query("SELECT * FROM clients WHERE num_client = '$num_client'");
			return $result;
		}
		
		public function checkNumClientWithIdComp($id_comp, $num_client) {
			$part = intval(substr($num_client, 0, 5));
			$result = mysql_query("SELECT * FROM clients WHERE id = '$part'");
			$row = mysql_fetch_array($result);
			
			if(intval($row["id_comp"]) == $id_comp) { return true; }
			else { return false; }
		}
		
		public function getCompanyWithId($id) {
			$result = mysql_query("SELECT * FROM companies WHERE id = '$id'");
			return $result;
		}
		
		public function getReductionWithId($id) {
			$result = mysql_query("SELECT * FROM reductions WHERE id = '$id'");
			return $result;
		}
		
		public function getReductionDescriptionWithId($id) {
			$result = mysql_query("SELECT description FROM reductions WHERE id = '$id'");
			return $result;
		}
		
		public function isPeopleUpdated($id) {
			$result = mysql_query("SELECT * FROM people WHERE id = '$id'");
			return $result;
		}
		
		public function isCompanyUpdated($id) {
			$result = mysql_query("SELECT * FROM companies WHERE id = '$id'");
			return $result;
		}
		
		public function isClientUpdated($id) {
			$result = mysql_query("SELECT * FROM clients WHERE id = '$id'");
			return $result;
		}
		
		public function checkLog($username, $password) {
			$result = mysql_query("SELECT * FROM people WHERE username = '$username' AND password = '$password'");
			return $result;
		}
		
		public function checkCompanyLog($company_name, $password) {
			$result = mysql_query("SELECT * FROM companies WHERE name = '$company_name' AND password = '$password'");
			return $result;
		}
		
		public function removeClient($num_client) {
			$result = mysql_query("DELETE FROM clients WHERE num_client = '$num_client'");
			
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
		
		public function removeOffer($id) {
			$result = mysql_query("DELETE FROM offers WHERE id = '$id'");
			
			if ($result) { return true; }
			else {
				if( mysql_errno() == 1062) { return true; }
				else { return false; }
			}
		}
	}
?>