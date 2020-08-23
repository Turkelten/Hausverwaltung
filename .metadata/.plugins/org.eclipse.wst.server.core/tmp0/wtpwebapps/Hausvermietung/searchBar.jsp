<div class="searchsidebar-container">	
			<h2>Suchen</h2>
			<div class="searchsidebar">	    
		      	<form action="FilterServlet" method="post"><br/>
		      		<label style="float: left">Beschreibung:</label> <br/>
					<input type="text" name="beschreibung"> <br/>
					<label style="float: left">Marke:</label><br/>
					<select style="padding-top: 30px" name="marke">
						<option value=""> </option>
					  	<option value="vw">VW</option>
					  	<option value="bmw">BMW</option>
					  	<option value="mercedes">Mercedes</option>
					  	<option value="audi">Audi</option>
					</select><br/>
					<label style="float: left">Bezeichnung:</label> <br/>
					<input type="text" name="bezeichnung"> <br/>
					<label style="float: left">Motortyp:</label><br/> 
					<select  name="motor">
						<option value=""> </option>
					  	<option value="benzin">Benzin</option>
					  	<option value="diesel">Deisel</option>
					  	<option value="elektro">Elektro</option>
					</select><br/>
					<label style="float: left">Min. Leistung(PS):</label><br/>
					<input type="number" min="0" step="10" name="leistung"> <br/>
					<label style="float: left">Max. Verbrauch:</label><br/>
					<input type="number" min="0" step="0.5" name="verbrauch"> <br/>
					<label style="float: left">Größe:</label><br/>
					<select  name="groesse">
						<option value=""> </option>
					  	<option value="3">3-Türer</option>
					  	<option value="5">5-Türer</option>
				  	</select><br/>
				    <label style="float: left">Max KM-Stand:</label>
					<input type="number" min="0" step="500" name="kmStand"> <br/>
					<br/><button type="submit">Suchen</button> <button type="reset">Reset</button>
				</form>
			</div>
		</div>