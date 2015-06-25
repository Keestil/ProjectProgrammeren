**Rapport Game-app**

**Kees Til**

**Introductie:**

Mijn app is een game, een 2D game waarbij de speler als doel heeft een zo hoog mogelijke score te behalen door raketten te ontwijken. De speler hangt aan de linkerkant van het scherm in de lucht en kan door te klikken vliegen, en door niet te klikken zakt hij naar beneden. Verder zullen van rechts projectielen komen, waarbij de speler kan sterven of powerups kan pakken. Als de speler sterft wordt er een explosie afgespeeld en zal in het scherm staan of de speler zijn highscore heeft verbeterd of niet. Dit slaat de app vervolgens op.

De doelgroep die ik hiermee will bereiken zijn voornamelijk de mensen die tijd moeten doden. Zo zullen mensen die moeten wachten op het stadsdeelkantoor of voor de trein, de tijd kunnen doden met deze game-app. 


**Technisch ontwerp:**


Ik heb gekozen om Android Studio te gebruiken in plaats van game-engines als Unity, omdat ik het gevoel had meer te kunnen leren met Android Studio. Bovendien had ik de N-puzzle app al gemaakt met Android Studio dus de software is mij meer bekend. Achteraf gezien ben ik nog steeds trots op mijn keuze hiervoor, ondanks het feit dat het spelletje er waarschijnlijk beter had uitgezien in Unity.

De java classes in mijn project zijn:

- MainActivity

  Hierin maak ik de MediaPlayers en de SharedPreferences aan, omdat deze class extends van Activity moet ik dat hier doen.     Verder zorg ik ervoor dat de muziek stopt wanneer men de app afsluit en wanneer de gebruiken het geluid wil uitzetten via    het menu. Verder zal het spel in fullscreen-modus gaan. Deze klasse is eigenlijk het minst interessant van allemaal, gezien   het feit dat de game zich voornamelijk in de GamePanel afspeelt. 
 
- Hero

  Hierin zet ik de startpositie van de helicopter. Verder bepaal ik de hoogte en breedte van de spritesheet die de speler      wil gebruiken om te snijden in x aantal stukken, ik heb ervoor gekozen om alleen horizontale spritesheets te kiezen omdat    dat makkelijker te programmeren was. De lijst met gesneden frames geef ik vervolgens door aan de SpriteSlide class en ik     geef ook de wachttijd mee die de SpriteSlide moet aanhouden na elke frame in deze lijst.

  Daarnaast moet men wat met de helicopter kunnen doen, wat in de update method gebeurd. Eerst moet de animatie natuurlijk     gemaakt worden, wat gebeurt in de SpriteSlide class. Vervolgens wil ik met behulp van een boolean check bepalen of de        helicopter naar beneden moet vliegen of naar boven, de y-coordinaat is als hij naar beneden gaat en negatief als hij naar    boven gaat. Om te voorkomen dat de helicopter uit het scherm vliegt hebben ik nog een if-statement gebruikt. Verder          implementeer ik een paar methods als de set-get-resetScore() en de is-setPlaying() method, die gebruikt wordt om de speler   te resetten als hij afgaat.
  
  In de tekenmethod wil ik slechts de frames tekenen, dus ik gebruik de SpriteSlide class waar een lijst met de frames in is   gemaakt. Ik heb dit niet in de Hero class gedaan omdat ik de frames na elkaar wil tekenen en dit gebeurt
  in de SpriteSlide class, zo heb ik het ook gedaan in de Missles/Explosion class.

- Explosions

    In deze klasse maak ik explosies aan, eigenlijk doe ik precies hetzelfde als in de Hero class. Ik heb ervoor gekozen om     de animatie meer dan 1 keer af te spelen voor een extra dramatisch effect. Als men de animatie slechts 1 keer wil            afspelen kan er gebruikt worden gemaak van een boolean check als playedOnce in de SpriteSlide class.

- Missles

  Hier maakt men de rakketten/power-ups aan. ik maak de animatie zoals in hero-class. Het verschil is hier dat de raketten     in de update method naar links moeten gaan, daar waar ik bij de Hero class kijken voor omlaag/omhoog. De methode             get/setSpeed() helpt mij om de snelheid van de raketten te controleren.

- GameThread

  In deze thread maken we de game loop, de game loop is het hart van elk spel. Wat we hier doen is data inputten, het spel     updaten en vervolgens deze update uitvoeren. En dit doen we dan oneindig vaak, hieronder is een versimpelde weergave         hiervan:
  
  ![Image of gameloop](http://obviam.net/wp-content/uploads/2010/08/Gameloop-1.png)
  
  In onze Activity maken we een surfaceView, hierin kunnen we als het ware onze app op tekenen. Het volgende plaatje geeft     dat goed weer. 
  
  ![Image of surfaceView](http://obviam.net/wp-content/uploads/2010/08/Gameloop-3.png)
  
  Omdat we willen dat de in de GamePanel alles gebeurt hebben we de getHolder().add.Callback(this) in de onCreate method()     toegevoegd. Nu alles goedstaat hoeven we alleen de gameloop te starten en te controleren in de gamepanel (meer uitleg        hierover komt terug in het GamePanel stukje). In de GameThread zelf updaten we de game en tekenen we de nieuwe staat en      doen dit oneindig lang. omdat we dus oneindig lang de taak input-update-render uitvoeren, zult u in de code merken dat
  dit veel geheugen kost, en dat ik probeer zo efficient mogelijk te programmeren om dit te voorkomen.

- GamePanel

  In de GamePanel gaat de game starten. Eerst importeer ik de data die we in MainActivity hebben doorgegeven en maak ik de     thread aan. De Holder wordt aangemaakt om SurfaceView te gebruiken en setFocusable(true) blijkt de app beter te laten        werken. Ik heb nu een surfaceDestroyed en een surfaceCreated method, de surfaceChanged method gebruik ik niet, maar het      programma flipt als ik die weghaal.
  
  In de surfaceDestroyed method, zorge ik ervoor dat de thread goed afsluit en in de surfaceCreated method zorg ik ervoor      dat de thread afgespeeld wordt. Ik heb ervoor gekozen om the surfaceDestroyed boven de surfaceCreated te zetten voor beter   overzicht, het is conventioneel om de surfacedestroyed er eigenlijk achter te plakken.
  
  In de surfaceCreate methode maak ik de achtergrond, de speler en de explosies, verder starten we de thread. 
  
  In de ontouchevent kijk ik of de speler speelt of niet, en of hij het scherm heeft aangeraakt of niet. Verder kijk ik        ook of de speler voor het eerst speelt zodat het beeld niet freezed en of hij de muziek moet afspelen of niet, ik wil niet
  dat de muziek pas afspelen als de speler geklikt heeft op het scherm.
  
  In de update method speel ik de achtergrondmuziek af, omdat het een infinite thread is, moet ik een counter zetten en        speel ik de muziek pas af als deze counter 0 is. Daarna tellen ik 1 op bij de counter zodat hij de muziek maar 1 keer        afspeelt. De counter wordt in de newGame() method weer op 0 gezet.
  
  **speler speelt:**
  
  Als de speler speelt wil ik dat de game the Missles update en natuurlijk de speler. De speler update ik meteen, maar         bij de missles wil ik we de update method om een bepaalde tijd updaten, anders worden ze allemaal tegelijkertijd afgevuurd.
  Dus ik begin met het zetten van een timer. Ik maak een lege lijst aan en zeg wacht x aantal milliseconden voordat je de      volgende missle maakt. Naarmate de speler een hogere score haalt zal het aanmaken van de rakketten sneller gaan. 
  
  Als ik de lijst heb gemaakt met missles, ga ik de snelheid van de missles aanpassen en vervolgens update ik deze.            Afhankelijk van de identiteit identiteit van de missle zal de speler sterven of veranderen in een helicopter/ruimteschip.
  
  Voor memory issues kies ik er vervolgens voor om de missles uit de lijst te halen als ze buiten het beeld vliegen. Het       moment dat ze verdwijnen krijgt de speler ook punten. Voor kleine missles en powerups krijg je 1 punt en voor grote          raketten 5. 
  
  **speler speelt niet:**
  
  Dit is het punt waar we het spel willen freezen of niet. Ik heb een freeze boolean aan het begin true gezet, maar deze       meteen op false gezet wanneer de speler begon. Als de speler dus af is en in de fase komt waar hij niet speelt zet ik        freeze dus op true. Nu wil ik dat tijdens de freeze state de volgende dingen gebeuren:
  
  - De bom wordt gemaakt,afgevuurd en geupdate
  - De speler begint niet aan een nieuwe game
  - De muziek stopt
  - Het geluid van de bom wordt afgespeeld.
  - een timer zorgt ervoor dat dit allemaal gebeurt, en als deze voorbij is begint het nieuwe spel.
  
  Als de speler een nieuw spel begint, moet zijn score opgeslagen worden en de geluiden stoppen. Verder moeten allerlei
  variabelen weer op default worden gezet en speelt de speler het spel opnieuw.

  In de draw method teken ik alles wat ik maar tekenen wil en de andere methodes spreken eigenlijk wel voor zich, het zijn     met name hulpfuncties die nesting voorkomen.
  
- Object

  Deze klasse bevat alle methods die Hero/Explosion/Missle gemeen hebben. Denk hierbij aan een setX(), setY() functie etc....
  Ik zat te twijfelen of ik unieke methods van deze klassen als setSpeed() en setScore() hier ook in moest doen, maar ik heb   besloten dat niet te doen. Zo raakt de lezer niet in de war, in de zin dat de methods niet als universeel worden gezien.

- SpriteSlide

  Hier regel ik de animatie. Wat ik kortgezegd doen is een lijst met frames frames die ik in Hero/Missle/Explosion heb         gemaakt snel achter elkaar af spelen. De waitingTime bepaald hoeveel tijd er tussen het afspelen van de volgende frames      ziet. In de update method zet ik een timer en zeg ik kortgezegd, ga naar de volgende frame als ik voorbij de waitTime ben    en zet de timer weer op 0. Om errors te voorkomen begin ik weer bij het eerste element van onze lijst als currentFrame       gelijk is aan de laatste frame van de lijst. De getImage() method geeft mij de frame waar ik zijn, die gebruikt wordt in de   draw methods van Hero/Explosion/Missles class.
  
  
**Moeilijkheden tijdens het project:**

Tijdens dit project ben ik op vele moeilijkheden gestuit, om u een gehele waslijst te besparen bespreek ik hier 5 moeilijkheden waar ik het langst mee bezig mee ben geweest.

*1) Het maken van de gameloop.*

Ik heb in het begin een paar Gameloops gecopy-paste om te zien of ze werkten, echter was dit vrij naar omdat elke game loop anders is en er makkelijk problemen kunnenn ontstaan. Zo heb ik mijn programma opnieuw moeten programmeren omdat ik geen goede update method had, maar met behulp van de website http://obviam.net/ ben ik erachter gekomen hoe ik de ideale gameloop voor deze specifieke game moest maken. Ik raad iedereen die een game-app maakt deze website te bezoeken, er is zeer veel informatie te vinden!

*2) De animatie van de sprites maken.*

Dit was in het begin vrij lastig voor mij, omdat ik er nooit mee had gewerkt. Een van mijn doelen was ook om dit onder de knie te krijgen voordat ik aan het project begon. De moeilijkheid lag hem in de animatieklasse, gelukkig wist ik dat je hoogstwaarschijnlijk zoals in tekenfilms de plaatjes snel achter elkaar moest spelen, deze tactiek heb ik dan ook toe moeten passen. Na veel documentaties gelezen te hebben over de bitmap arraylists en de timer-truuk met de system.nanotime(), kwam ik wel mooi uit. Hier kwam ik er ook achter dat Object-orientated programming heel handig zou worden, omdat mensen op stackoverflow me dit adviseerden. De Missles/Explosion en Hero waren daarom niet zo moeilijk te maken nadat die Spriteslide klaar was.

*3) De projectielen van rechts naar links laten gaan om een bepaalde tijd.*

Dit bleek veel moeilijker dan gedacht, ik wou eerst een lijst maken en daarin allerlei projectielen stoppen, echter doordat de gamethread een infinite loop heeft werd deze lijst ook oneindig lang. Dat is natuurlijk niet handig en het programma crashte dan ook meteen. Uiteindelijk heeft Jaap me verteld dat ik misschien een eindige lijst kon maken waarbij ik willekeurige elementen eruit pak en deze vervolgens teken. Dit heb ik ook geprobeerd maar ik kwam niet echt mooi uit. Toen bedacht ik mij dat de lijst nooit oneindig lang wordt als je de projectielen verwijderd uit de lijst als dezen uit het beeld verdwenen. Het algoritme werkte maar de animatie niet, Ben zag echter dat ik de lijst niet had aangemaakt en java had dat niet als error aangegeven. Na het maken van deze animatie was de rest niet meer zo immens lastig.

*4) De freeze-state maken.*

Dit was een struikelblokje, echter met ernstig veel boolean statements kwam ik er wel uit. Ik denk achteraf dat dit misschien wel makkelijker had gekund maar ik zie niet in hoe. Het moeilijkste was om na te gaan wat ik nou precies wilde doen in deze freezedstate en hoe dit erin te verwerken. Voornamelijk de combinaties van geluid en animatie waren irritant.

*5) Het geluid regelen.*

Mediaplayer is niet mijn beste vriend. In een gameloop weet je eigenlijk al dat de muziek oneindig vaak afgespeeld zal worden als je de geluiden gewoon afspeelt. Echter met behulp van een best wel mooi algoritme heb ik het voor elkaar weten te krijgen om de muziek en de explosies maar één keer af te spelen. In het begin herkende mijn android studio de mp3/wav files niet, maar na wat documentatie gelezen te hebben kwam dit wel goed. Dit was wel vrij zonde van mijn tijd gezien het feit ik ook nog misschien met die extra uren een database met allemaal scores van verschillende spelers kon maken. 

**Veranderingen TOV van mijn DESIGN.MD:**

Eigenlijk niet heel veel, mijn product voldoet aan de minimale eisen en zelfs aan het merendeel van de bijzaken heb ik erin verwerkt. De surfaceview die ik toentertijd wou gebruiken heb ik ook geimplementeerd. Het grote verschil zit hem in de opmaak en de classes die ik heb gebruikt. 

Ik heb gekozen om alles in één activity class (xml) te doen. Eerst wilde ik ook een CharacterActivity maken waarbij de speler een karakter kon kiezen, echter zullen er dan heel veel extra if-statements in de code voorkomen. Bovendien zullen de geschaalde sprites niet evengroot zijn waardoor ik voor iedere aparte sprite een andere scorestate zou moeten maken(anders is de highscore niet eerlijk). Ik zie niet zoveel toegevoegde waarde in extra spelers en probeer de app zo simplistische mogelijk te laten zijn, doch leuk. Vandaar de keuze van maar één speler. 

De LeaderboardActivity vond ik ook onnodig gezien het feit dat de bestscore altijd rechtsonder in beeld zal staan en waarschijnlijk slechts 1 speler de app zal gebruiken. Het implementeren per naam is dus een beetje overbodig, tenzij ik een online database zou maken waarbij mensen over de hele wereld hun highscores opslaan, maar dat bleek teveel werk te zijn.

Daarnaast bleken SaveScore en Gravity niet nodig te zijn, doordat ik de onTouchevent functie en de SharedPreferences ontdekte die het leven een stuk makkelijker maakten. 

Tnslott wou ik toentertijd ook een bewegende achtergrond maken, echter vond dat het scherm al afleidend genoeg was met al die bewegende projectielen en de bewegende speler. Ik heb dus gekozen om dit niet te doen en mijn tijd beter te kunnen investeren in het implementeren van geluid.

Wat ik er nog bij heb gedaan is de optie om het geluid aan/uit te kunnen zetten in het menu. Ik merkte dat het geluid een beetje irritant kan zijn dus deze optie wou ik graag toevoegen aan de app.

**Korte samenvatting**

*Mijn App kan de volgende dingen:*

- Sprites afspelen.
- Speler besturen naar boven en onder.
- Verschillende projectielen aanmaken om een bepaalde tijd en dezen van rechts naar links laten gaan.
- Verschillende eigenschappen meegeven aan deze projectielen, zoals het veranderen in een schotel of het exploderen.
- Score bijhouden en opslaan.
- Explosie afspelen met geluid.
- Achtergrondmuziek maken.
- Muziek aan/uit doen.
- Bepalen of een speler zijn score heeft verbroken of niet.

*Eventueele tekortkomingen:*

- Toegankelijkheid voor elke mobiel, niet alleen de nexus 5.
- Variatie in power-ups.
- Leaderboards voor alle gebruikers van de app, dus op verschillende telefoons.
- Muren boven en onder waar de speler door kan sterven.
- Meerdere characters.
- Meerdere achtergronden.
- Zelf muziek als achtergrondmuziek instellen.
- Cheat-codes

**Bronvermelding**

- http://obviam.net/index.php/a-very-basic-the-game-loop-for-android/
- http://obviam.net/index.php/the-android-game-loop/
- http://obviam.net/index.php/sprite-animation-with-android/
- http://docs.oracle.com/javase/tutorial/java/concepts/index.html
- http://stackoverflow.com/questions/19881635/cutting-sprite-sheets-into-individual-bitmaps-dynamically-with-variable-frame-si
- http://www.edu4java.com/en/androidgame/androidgame1.html
- https://www.youtube.com/watch?v=V1ocJmXeQ28
- https://www.youtube.com/watch?v=I1qTZaUcFX0
- https://www.youtube.com/watch?v=rJcm5Oyi3YA
- http://developer.android.com/develop/index.html

