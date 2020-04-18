<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.visible {
	opacity: "${Visible}";
}

.hide {
	opacity: 0;
}
</style>
<script>
	function myFunction() {
		var a = document.getElementById("DepositWithdraw");
		a.className += "hide";
		var b = document.getElementById("msg").textContent;
		if (b == "Your authentication is successful. ") {
			a.classList.remove("hide");
			a.classList.add("visible");
		}
	}
</script>
<title>ATM</title>

</head>
<body style="background-color: #F9EAC6" onload="myFunction()">

	<h2 style="text-align: center" class="display-4">Welcome to the ATM</h2>
	<form action="http://localhost:8080/Project/Main"
		style="text-align: center" class="container">

		<br>
		<h5 class="form-inline justify-content-center row">
			Enter your UserID: &nbsp; <input
				class="form-control col-sm-3 col-md-3 col-xs-6" type="text"
				name="userID" required>
		</h5>
		<input class="btn btn-primary" type="submit" id="sub" name="submit"
			value="Submit"><br> 
		<p style="color: ${messagecolor}" id="msg">${message}</p>
		<c:remove var="message" scope="session" />

	</form>

<br>
	<div id="DepositWithdraw">

		<form class="container " method="post" style="background-color: #F9EAC6">
			<h4 style="text-align: center">Welcome ${username} :)</h4>
			<br><br>
			<div class="row">
				<div class="input-group col-md-1"></div>
				<div class="input-group mb-3 col-sm-6 col-xs-4 col-md-5">
					<input type="text" class="form-control" placeholder="Amount" data-toggle="tooltip" data-placement="bottom" title="Enter amount to be withdrawn"
						aria-label="Enter amount to be withdrawn" name="WithdrawAmount"
						aria-describedby="button-addon2">
					<div class="input-group-append">
						<input class="btn btn-primary" name="action" value="Withdraw"
							type="submit" id="button-addon2"><br>
					</div>
				</div>
				<div class="input-group mb-3 col-sm-6 col-xs-4 col-md-5">
					<input type="text" class="form-control" placeholder="Amount" data-toggle="tooltip" data-placement="bottom" title="Enter amount to be deposited"
						aria-label="Enter the amount to be deposited" name="DepositAmount"
						aria-describedby="button-addon2">
					<div class="input-group-append">
						<input class="btn btn-primary" name="action" value="Deposit"
							type="submit" id="button-addon2">
					</div>
				</div>
				<div class="input-group col-md-1"></div>
			</div><br>
			<p style="color: ${wd}; text-align: center">${withdrawDeposit}</p>
			<c:remove var="message" scope="session" />
			<h5 style="text-align: center">Your current balance is : &nbsp;
				${ updatedbalance }</h5>
			<c:remove var="updatedbalance" scope="session" />
		</form>
	</div>
</body>


</html>
