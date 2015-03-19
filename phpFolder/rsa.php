<?php
	class RSA {
		private $phrase = null;
		private $public_file = null;
		private $private_file = null;
		
		function __construct($puf, $prf, $ph) {
			$this->public_file = $puf;
			$this->private_file = $prf;
			$this->phrase = $ph;
		}
		
		function __destruct() { }
		
		public function get_public_key() {
			try {
				$fp = fopen($this->public_file, 'r');
				$pub_key = fread($fp, 2048);
				fclose($fp);
				$pkeyid = openssl_get_publickey($pub_key);
				if(openssl_open($sealed, $open, $env_key, $pkeyid)) {
					return $open;
				} else {
					return false;
				}
				//$str = file_get_contents($this->public_file);
				//return openssl_get_publickey($str);
			} catch( Exception $e) {
				return false;
			}
		}
		
		public function get_private_key() {
			try {
				$str = file_get_contents($this->private_file);
				return openssl_get_privatekey(array($str,$this->phrase));
			}
			catch( Exception $e) {
				return false;
			}
		}
		
		public function encrypt($data) {
			$key = $this->get_public_key();
			if(!$key) {
				return false;
			} else {
				openssl_public_encrypt($data,$en,$key);
				return $en;
			}
		}
		
		public function decrypt($data) {
			$key = $this->get_private_key();
			if(!$key) {
				return false;
			} else {
				openssl_private_decrypt($data,$de,$key);
				return $de;
			}
		}
	}
?>