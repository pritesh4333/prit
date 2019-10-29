<?php

	//	echo $filename;
	$myfile = fopen('TextFiles/location.text', "r") or die("Unable to open file!");
 	$data = fgets($myfile);
	fclose($myfile);
  echo "<a href='".$data."'>$data</a>";
	 

 
?>