Ten eerste zal ik hierin vertellen wat onder mijn ogen een minimal/viable product is. Voor een minimal viable product
moet men ge-entertained worden, anders is het een vrij waardeloos programma. Om dat te doen moet dit er zoiezo inzitten:

- Het product moet een bewegende sprite hebben, die door zwaartekracht naar beneden kan worden getrokken
- Men moet een score kunnen halen en deze kunnen verbreken, scores moeten dus opgeslagen worden.
- Men moet kunnen sterven door vliegende projectielen.
- Men moet opnieuw kunnen beginnen.

**Bijzaken:**

- Muziek zou wel fijn zijn.
- Meer characters kiezen.
- explosie/sterf animatie.
- variatie in projectielen.
- boosts (onsterfelijk zijn, schieten).

**Classes die ik heb geimplementeerd:**

*xml*

- MainActivity
- CharacterActivity
- CustomlistAdapter
- GameActivity
- LeaderActivity

*java*

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

