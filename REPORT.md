**Rapport Game-app**
**Kees Til**

**Introductie:**

Mijn app is een game, een 2D game waarbij de speler als doel heeft een zo hoog mogelijke score te behalen door raketten te ontwijken. De speler hangt aan de linkerkant van het scherm in de lucht en kan door te klikken vliegen, en door niet te klikken zakt hij naar beneden. Verder zullen van rechts projectielen komen, waarbij de speler kan sterven of powerups kan pakken. Als de speler sterft wordt er een explosie afgespeeld en zal in het scherm staan of de speler zijn highscore heeft verbeterd of niet. Dit slaat de app vervolgens op.

De doelgroep die ik hiermee will bereiken zijn voornamelijk de mensen die tijd moeten doden. Zo zullen mensen die moeten wachten op het stadsdeelkantoor of voor de trein, de tijd kunnen doden met deze game-app. 


**Technisch ontwerp:**


Ik heb gekozen om Android Studio te gebruiken in plaats van game-engines als Unity, omdat ik het gevoel had meer te kunnen leren met Android Studio. Bovendien had ik de N-puzzle app al gemaakt met Android Studio dus de software is mij meer bekend. Achteraf gezien ben ik nog steeds trots op mijn keuze hiervoor, ondanks het feit dat het spelletje er waarschijnlijk beter had uitgezien in Unity.

De java classes in mijn project zijn:

- MainActivity

  Hierin maak ik de MediaPlayers en de SharedPreferences aan, omdat deze class extends van Activity moet ik dat hier doen.     Verder zorg ik ervoor dat de muziek stopt wanneer men de app afsluit en zal het beeld fullscreen gaan. Deze klasse is        eigenlijk het minst interessant van allemaal, gezien het feit dat de game zich voornamelijk in de GamePanel afspeelt. 
 
- Hero

  Hierin zetten we de startpositie van de helicopter. Verder bepalen we de hoogte en breedte van de spritesheet die de speler   wil gebruiken om te snijden in x aantal stukken, ik heb ervoor gekozen om alleen horizontale spritesheets te kiezen omdat    dat makkelijker te programmeren was. De lijst met gesneden frames geef ik vervolgens door aan de SpriteSlide class en ik     geef ook de wachttijd mee die de SpriteSlide moet aanhouden na elke frame in deze lijst.

  Daarnaast moet men wat met de helicopter kunnen doen, wat in de update method gebeurt. Eerst moet de animatie natuurlijk     gemaakt worden, wat gebeurt in de SpriteSlide class. Vervolgens willen we met behulp van een boolean check bepalen of de     helicopter naar beneden moet vliegen of naar boven, de y-coordinaat is als hij naar beneden gaat en negatief als hij naar    boven gaat. Om te voorkomen dat de helicopter uit het scherm vliegt hebben ik nog een if-statement gebruikt. Verder          implementeren we een paar methods als de set-get-resetScore() en de is-setPlaying() method, die gebruikt wordt om de speler   te resetten als hij afgaat.
  
  In de tekenmethod willen we slechts de frames tekenen, dus we gebruiken de SpriteSlide class waar een lijst met de frames    in is gemaakt. Ik heb dit niet in de Hero class gedaan omdat we de frames na elkaar willen tekenen en dit gebeurt
  in de SpriteSlide class, zo heb ik het ook gedaan in de Missles/Explosion class.

- Explosions

  In deze klasse maken we explosies aan, eigenlijk doen we precies hetzelfde als in de Hero class. Ik heb ervoor gekozen om    de animatie meer dan 1 keer af te spelen voor een extra dramatisch effect. Als men de animatie slechts 1 keer wil afspelen   kan er gebruikt worden gemaak van een boolean check als playedOnce in de SpriteSlide class.

- Missles

  Hier maakt men de rakketten/power-ups aan. we maken de animatie zoals in hero-class. Het verschil is hier dat de raketten    in de update method naar links moeten gaan, daar waar we bij de Hero class kijken voor omlaag/omhoog. De methode             get/setSpeed() helpen ons om de snelheid van de raketten te controleren.

- GameThread

  In deze thread zorgen we dat de taken worden uitgevoerd, in dit geval dus het tekenen en updaten van het spel. De gameloop   wordt hierin uitgevoerd. Deze klasse is dus de basis van het spel en in mijn ogen het belangrijkste deel van het project.

- GamePanel

  Hierin wordt de game afgespeeld, we tekenen hier alles (mbv van de GameThread en SurfaceView) en zorgen dat de animaties op   de juiste momenten worden afgespeeld. Verder worden hier de functies gemaakt die invloed hebben op de gameplay, zoals  het
  op en neer stijgen van de helicopter en het freezen van het beeld op het moment dat de speler af is.

- Object

  Deze klasse bevat alle methods die Hero/Explosion/Missle gemeen hebben. Denk hierbij aan een setX(), setY() functie etc....
  Ik zat te twijfelen of ik unieke methods van deze klassen als setSpeed() en setScore() hier ook in moest doen, maar ik heb   besloten dat niet te doen. Zo raakt de lezer niet in de war, in de zin dat de methods niet als universeel worden gezien.

- SpriteSlide

  Hier regelen we de animatie. Wat we kortgezegd doen is het de frames die we in Hero/Missle/Explosion hebben gemaakt snel     achter elkaar spelen. De waiting time bepaald hoeveel tijd ertussen zit.

**Veranderingen tov van mijn DESIGN.MD:**

Eigenlijk niet heel veel, mijn product voldoet aan de minimale eisen en zelfs aan het merendeel van de bijzaken heb ik erin verwerkt. De surfaceview die ik toentertijd wou gebruiken heb ik ook geimplementeerd. Het grote verschil zit hem in de opmaak en de classes die ik heb gebruikt. 

Ik heb gekozen om alles in één activity class (xml) te doen. Eerst wilde ik ook een CharacterActivity maken waarbij de speler een karakter kon kiezen, echter zullen er dan heel veel extra if-statements in de code voorkomen. Bovendien zullen de geschaalde sprites niet evengroot zijn waardoor ik voor iedere aparte sprite een andere scorestate zou moeten maken(anders is de highscore niet eerlijk). Ik zie niet zoveel toegevoegde waarde in extra spelers en probeer de app zo simplistische mogelijk te laten zijn, doch leuk. Vandaar de keuze van maar één speler. De LeaderboardActivity vond ik ook onnodig gezien het feit dat de bestscore altijd rechtsonder in beeld zal staan en waarschijnlijk slechts 1 speler de app zal gebruiken. Het implementeren per naam is dus een beetje overbodig, tenzij ik een online database zou maken waarbij mensen over de hele wereld hun highscores opslaan, maar dat bleek teveel werk te zijn. Ten slotte bleken SaveScore en Gravity niet nodig te zijn, doordat ik de onTouchevent functie en de SharedPreferences ontdekte die het leven een stuk makkelijker maakten. 

