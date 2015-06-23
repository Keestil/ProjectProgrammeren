Rapport Game-app

Kees Til

Introductie:

Mijn app is een game, een 2D game waarbij de speler als doel heeft een zo hoog mogelijke score te behalen door raketten te ontwijken. De speler hangt aan de linkerkant van het scherm in de lucht en kan door te klikken vliegen, en door niet te klikken zakt hij naar beneden. Verder zullen van rechts projectielen komen, waarbij de speler kan sterven of powerups kan pakken. Als de speler sterft wordt er een explosie afgespeeld en zal in het scherm staan of de speler zijn highscore heeft verbeterd of niet. Dit slaat de app vervolgens op.

De doelgroep die ik hiermee will bereiken zijn voornamelijk de mensen die tijd moeten doden. Zo zullen mensen die moeten wachten op het stadsdeelkantoor of voor de trein, de tijd kunnen doden met deze game-app. 


Technisch ontwerp:


Ik heb gekozen om Android Studio te gebruiken in plaats van game-engines als Unity, omdat ik het gevoel had meer te kunnen leren met Android Studio. Bovendien had ik de N-puzzle app al gemaakt met Android Studio dus de software is mij meer bekend. Achteraf gezien ben ik nog steeds trots op mijn keuze hiervoor, ondanks het feit dat het spelletje er waarschijnlijk beter had uitgezien in Unity.

Ik heb vooral in java geprogrammeerd en heb gekozen om alles in één activity class (xml) te doen. Eerst wilde ik ook een CharacterSelection maken waarbij de speler een karakter kan kiezen, echter zal er dan in de code heel veel extra if-statements komen. Bovendien zullen de geschaalde sprites niet evengroot zijn waardoor ik voor iedere aparte sprite een andere scorestate zou moeten maken. Ik probeer de app zo simplistische mogelijk te laten zijn, doch leuk.

De java classes in mijn project zijn:

- MainActivity

 Hierin maak ik de MediaPlayers en de SharedPreferences aan, omdat deze class extends van Activity moet ik dat hier doen.     Verder zorg ik ervoor dat de muziek stopt wanneer men de app afsluit en zal het beeld fullscreen gaan. De GamePanel zal      meteen worden aangeroepen.
 
- Hero

 We zorgen ervoor met een boolean-check dat de speler weet wanneer hij omhoog moet en wanneer hij omlaag moet. Verder zorgen  we ervoor dat de speler niet uit het scherm kan vliegen. De animatie van de speler wordt verzorgd in de SpriteSlide, door de  spritesheet in stukjes te hakken en steeds na elkaar weer te geven maken we een animatie. 

- Explosions

  In deze klasse maken we explosies aan, eigenlijk doen we precies hetzelfde als in de Hero class. Ik heb ervoor gekozen om    de animatie meer dan 1 keer af te spelen voor een extra dramatisch effect. De animatie wordt door SpriteSlide geregeld op    dezelfde manier als de Hero class.

- Missles

  Hier maakt men de rakketten/power-ups aan. we maken de animatie zoals in hero-class. Het verschil is hier dat de raketten    in de update method naar links moeten gaan, daar waar we bij de Hero class kijken voor omlaag/omhoog. De methode             get/setSpeed() helpen ons om de snelheid van de raketten te controleren.

- GameThread

  In deze thread zorgen we dat de taken worden uitgevoerd, in dit geval dus het tekenen en updaten van het spel. De gameloop   wordt hierin uitgevoerd. Deze klasse is dus de basis van het spel en in mijn ogen het belangrijkste deel van het project.

- GamePanel

  Hierin wordt de game afgespeeld, we tekenen hier alles (mbv van de GameThread en SurfaceView) en zorgen dat de animaties op   de juiste momenten worden afgespeeld. Verder worden hier de functies gemaakt die invloed hebben op de gameplay, zoals  het
  op en neer stijgen van de helicopter en het freezen van het beeld op het moment dat de speler af is.

- Object

  Deze klasse bevat alle methods die Hero/Explosion/Missle gemeen hebben. Denk hierbij aan een setX(), setY() functie etc....

- SpriteSlide

  Hier regelen we de animatie. Wat we kortgezegd doen is het de frames die we in Hero/Missle/Explosion hebben gemaakt snel     achter elkaar spelen. De waiting time bepaald hoeveel tijd ertussen zit.

