<?php
 
$json_str = file_get_contents('php://input');
 
  


 // cretae file
	$myfiles = fopen("write.text", "w") or die("Unable to open file!"); 
	fwrite($myfiles, "");
	fclose($myfiles);
	 
 
	$Location =  $json_str;
 
		file_put_contents("write.text", $Location);
		//	echo $filename;
 
echo 'Done';



?>
