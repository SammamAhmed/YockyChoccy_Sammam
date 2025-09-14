# YockyChoccy
A simple Java Swing implementation of the classic combinatorial game often known as Chomp. Players take turns eating a chosen square and every square below and to the right of it. The poisoned “soap” square must be avoided. Whoever is forced to take it loses. The game offers selectable board sizes (5×5, 6×6, 7×7), a live status display, and a scrolling move log. Includes a very basic computer opponent that chooses a random valid move.

Features:

1. Java Swing GUI with colored grid and sidebar for status + log
2. Adjustable board size on startup
3. Turn tracking and move logging
4. Automatic detection of losing square and replay prompt
5. Simple AI opponent with random move selection avoiding poisoned square
