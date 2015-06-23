Ten eerste zal ik hierin vertellen wat onder mijn ogen een minimal/viable product is. Voor een minimal viable product
moet men ge-entertained worden, anders is het een vrij waardeloos programma. Om dat te doen moet dit er zoiezo inzitten:

1) Het product moet een bewegende sprite hebben, die door zwaartekracht naar beneden kan worden getrokken
2) Men moet een score kunnen halen en deze kunnen verbreken, scores moeten dus opgeslagen worden.
3) Men moet kunnen sterven door vliegende projectielen.
4) Men moet opnieuw kunnen beginnen.

bijzaken:
1) Muziek zou wel fijn zijn.
2) Meer characters kiezen.
3) explosie/sterf animatie.
4) variatie in projectielen.
5) boosts (onsterfelijk zijn, schieten).

Classes die ik heb geimplementeerd:
xml/java
- MainActivity
- CharacterActivity
- CustomlistAdapter
- GameActivity
- LeaderActivity

java
- GamePanel
- LeaderActivity
- GamePanelThread
- Sprite
- SaveScore
- Gravity

Keuzes die ik heb gemaakt:

Ik heb besloten om met een surfaceview te werken in plaats van een view, uit verscheidene bronnen blijkt een surfaceview
beter te werken dan een view voor animatie en games zoals hier staat:

http://stackoverflow.com/questions/1243433/android-difference-between-surfaceview-and-view

Verder is een gameloop belangrijk voor games en daarvoor heb ik deze tutorial gebruikt:
http://obviam.net/index.php/a-very-basic-the-game-loop-for-android/

