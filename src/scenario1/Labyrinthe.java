package scenario1;

import java.util.PriorityQueue;
import java.util.List;
import java.util.*;

public class Labyrinthe {
	
	 static class Cell {
	        int x, y;
	        double costFromStart;
	        double heuristicCost;
	        double finalCost;
	        Cell parent;

	        Cell(int x, int y) {
	            this.x = x;
	            this.y = y;
	        }
	    }

	    private static double calculateHeuristic(int x, int y, int endX, int endY) {
	        return Math.abs(x - endX) + Math.abs(y - endY); // Distance de Manhattan
	        //return Math.sqrt(Math.pow(x - endX, 2) + Math.pow(y - endY, 2)); Distance euclidienne
	    
	    }
	    
	    /**
	     * Implémente l'algorithme A* pour trouver le chemin le plus court dans une grille 2D.
	     * 
	     * @param map La grille représentant la carte, où chaque cellule est un caractère.
	     * @param start La cellule de départ.
	     * @param end La cellule de destination.
	     * 
	     * @return La liste des cellules formant le chemin le plus court de 'start' à 'end'.
	     *         Retourne une liste vide si aucun chemin n'est trouvé.
	     * 
	     * Utilise une file de priorité basée sur le coût total (distance parcourue + heuristique).
	     * Chaque cellule est évaluée pour son coût depuis le départ et son coût heuristique vers la destination.
	     */
	      public static List<Cell> aStar(char[][] map, Cell start, Cell end) {
	        int N = map.length, M = map[0].length;
	        PriorityQueue<Cell> openSet = new PriorityQueue<>((a, b) -> Double.compare(a.finalCost, b.finalCost));
	        boolean[][] closedSet = new boolean[N][M];
	        Cell[][] cells = new Cell[N][M];

	        for (int i = 0; i < N; i++) {
	            for (int j = 0; j < M; j++) {
	                cells[i][j] = new Cell(i, j);
	                cells[i][j].heuristicCost = calculateHeuristic(i, j, end.x, end.y);
	                cells[i][j].costFromStart = Double.POSITIVE_INFINITY;
	            }
	        }

	        start.costFromStart = 0;
	        start.finalCost = start.heuristicCost;
	        openSet.add(start);

	        while (!openSet.isEmpty()) {
	            Cell current = openSet.poll();
	            if (current.x == end.x && current.y == end.y) {
	                return reconstructPath(current);
	            }
	            closedSet[current.x][current.y] = true;

	            for (int[] dir : new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}) {
	                int x = current.x + dir[0], y = current.y + dir[1];

	                if (x < 0 || y < 0 || x >= N || y >= M || map[x][y] == '#' || map[x][y] == 'F' || closedSet[x][y]) continue;
	                
	                Cell neighbor = cells[x][y];
	                double tentativeCost = current.costFromStart + 1;

	                if (tentativeCost < neighbor.costFromStart) {
	                    neighbor.parent = current;
	                    neighbor.costFromStart = tentativeCost;
	                    neighbor.finalCost = tentativeCost + neighbor.heuristicCost;
	                    if (!openSet.contains(neighbor)) openSet.add(neighbor);
	                }
	            }
	        }

	        return Collections.emptyList(); // Aucun chemin trouvé
	    }
	      
	      /**
	       * Reconstruit le chemin trouvé par l'algorithme A*.
	       * 
	       * @param end La cellule de destination à partir de laquelle le chemin est reconstruit.
	       * 
	       * @return Une liste de cellules représentant le chemin du départ à la destination.
	       *         Commence par la cellule de départ et se termine par la cellule de destination.
	       * 
	       * Cette méthode remonte le chemin en suivant les références 'parent' des cellules,
	       * qui indiquent la provenance dans le chemin le plus court, depuis la destination
	       * jusqu'à la source.
	       */
	    private static List<Cell> reconstructPath(Cell end) {
	        LinkedList<Cell> path = new LinkedList<>();
	        while (end != null) {
	            path.addFirst(end);
	            end = end.parent;
	        }
	        return path;
	    }
    
   
	public static boolean burnAround(int x, int y, int N, int M, char[][] map) {
	    if (y != 0) {
	        if (map[y-1][x] == '.') map[y-1][x] = 'A';
	        else if (map[y-1][x] == 'S' || map[y-1][x] == 'D') return true;
	    }
	    if (x != 0) {
	        if (map[y][x-1] == '.') map[y][x-1] = 'A';
	        else if (map[y][x-1] == 'S' || map[y][x-1] == 'D') return true;
	    }
	    if (y != N-1) {
	        if (map[y+1][x] == '.') map[y+1][x] = 'A';
	        else if (map[y+1][x] == 'S' || map[y+1][x] == 'D') return true;
	    }
	    if (x != M-1) {
	        if (map[y][x+1] == '.') map[y][x+1] = 'A';
	        else if (map[y][x+1] == 'S' || map[y][x+1] == 'D') return true;
	    }
	    return false;
	}
	

    public static boolean canMoveDir(int x, int y, char dir, int N, int M, char[][] map) {
        switch (dir) {
            case 'T': return y != 0 && map[y-1][x] == '.';
            case 'B': return y != N-1 && map[y+1][x] == '.';
            case 'L': return x != 0 && map[y][x-1] == '.';
            case 'R': return x != M-1 && map[y][x+1] == '.';
            default: return false;
        }
    }

  
    public static int canMove(int x, int y, int N, int M, char[][] map) {
        int top = canMoveDir(x, y, 'T', N, M, map) ? 1 : 0;
        int bottom = canMoveDir(x, y, 'B', N, M, map) ? 1 : 0;
        int left = canMoveDir(x, y, 'L', N, M, map) ? 1 : 0;
        int right = canMoveDir(x, y, 'R', N, M, map) ? 1 : 0;
        return top + bottom + left + right;
    }

   
    public static boolean winMove(int x, int y, int N, int M, char[][] map) {
        return (y != 0 && map[y-1][x] == 'S') ||
               (y != N-1 && map[y+1][x] == 'S') ||
               (x != 0 && map[y][x-1] == 'S') ||
               (x != M-1 && map[y][x+1] == 'S');
    }

    public static boolean movePrisoner(int N, int M, char[][] map) {
        int[] prisonerPosition = new int[2];
        int[] exitPosition = new int[2];
        // Trouver les positions du prisonnier et de la sortie
        for (int j = 0; j < N; j++) {
            for (int k = 0; k < M; k++) {
                if (map[j][k] == 'S') {
                    exitPosition[0] = j;
                    exitPosition[1] = k;
                } else if (map[j][k] == 'D') {
                    prisonerPosition[0] = j;
                    prisonerPosition[1] = k;
                }
            }
        }

        int deltaX = prisonerPosition[0] - exitPosition[0];
        int deltaY = prisonerPosition[1] - exitPosition[1];

        if (winMove(prisonerPosition[0], prisonerPosition[1], N, M, map)) {
            return true;
        } else {
            int nbMove = canMove(prisonerPosition[0], prisonerPosition[1], N, M, map);
            if (nbMove > 0) {
                int x = prisonerPosition[0];
                int y = prisonerPosition[1];
                boolean top = canMoveDir(x, y, 'T', N, M, map);
                boolean bottom = canMoveDir(x, y, 'B', N, M, map);
                boolean left = canMoveDir(x, y, 'L', N, M, map);
                boolean right = canMoveDir(x, y, 'R', N, M, map);

                map[x][y] = 'L'; // Bloque la position actuelle
                if (nbMove == 1) {
                    // Si un seul mouvement est possible, effectuer ce mouvement
                    if (top) map[y-1][x] = 'D';
                    else if (bottom) map[y+1][x] = 'D';
                    else if (right) map[y][x+1] = 'D';
                    else if (left) map[y][x-1] = 'D';
                } else {
                    // Sinon, choisir le mouvement en fonction des deltas
                    if (deltaX > 0 && left) map[y][x-1] = 'D';
                    else if (deltaX < 0 && right) map[y][x+1] = 'D';
                    else if (deltaY < 0 && bottom) map[y+1][x] = 'D';
                    else if (deltaY > 0 && top) map[y-1][x] = 'D';
                    else if (top) map[y-1][x] = 'D';
                    else if (bottom) map[y+1][x] = 'D';
                    else if (right) map[y][x+1] = 'D';
                    else if (left) map[y][x-1] = 'D';
                }
                return true;
            }
        }
        return false;
    }
    
    public static char runInstance(int N, int M, char[][] map) {
        int turn = 0;
        while (turn < N * M) {
        	
            // Propagation initiale du feu
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    if (map[j][k] == 'A') {
                        map[j][k] = 'F';
                    }
                }
            }

            // Vérifier et propager le feu
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    if (map[j][k] == 'F') {
                        if (burnAround(k, j, N, M, map)) return 'N';
                    }
                }
            }

            // Déplacer le prisonnier
            if (movePrisoner(N, M, map)) return 'Y';

            // Affichage de l'état actuel du labyrinthe
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    System.out.print(map[j][k]);
                }
                System.out.println();
            }
            System.out.println();

            turn++;
        }
        return 'N';
    }

    
    /*
    private static void propagateFire(int N, int M, char[][] map) {
        char[][] newMap = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                newMap[i][j] = map[i][j]; // Copie initiale
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 'F') {
                    // Haut
                    if (i > 0 && (map[i - 1][j] == '.' || map[i - 1][j] == 'D')) newMap[i - 1][j] = 'F';
                    // Bas
                    if (i < N - 1 && (map[i + 1][j] == '.' || map[i + 1][j] == 'D')) newMap[i + 1][j] = 'F';
                    // Droite
                    if (j < M - 1 && (map[i][j + 1] == '.' || map[i][j + 1] == 'D')) newMap[i][j + 1] = 'F';
                    // Gauche
                    if (j > 0 && (map[i][j - 1] == '.' || map[i][j - 1] == 'D')) newMap[i][j - 1] = 'F';
                }
            }
        }

        // Mise à jour de la grille
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = newMap[i][j];
            }
        }
    }
	*/

    private static void printLabyrinth(char[][] map, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer un nombre : ") ;
        int numberOfLabyrinths = scanner.nextInt(); // Nombre de labyrinthes à traiter

        for (int labyrinths = 0; labyrinths < numberOfLabyrinths; labyrinths++) {
            int N = scanner.nextInt(); // Nombre de lignes pour le labyrinthe actuel
            int M = scanner.nextInt(); // Nombre de colonnes pour le labyrinthe actuel
            scanner.nextLine(); // Consommer le reste de la ligne

            char[][] map = new char[N][M];
            for (int i = 0; i < N; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < M; j++) {
                    map[i][j] = line.charAt(j);
                }
            }

            Labyrinthe.Cell start = null, end = null;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == 'D') {
                        start = new Labyrinthe.Cell(i, j);
                    }
                    if (map[i][j] == 'S') {
                        end = new Labyrinthe.Cell(i, j);
                    }
                }
            }

            if (start != null && end != null) {
                List<Labyrinthe.Cell> path = Labyrinthe.aStar(map, start, end);
                char result = path.isEmpty() ? 'N' : 'Y';
                if(result == 'Y') {
                	System.out.println("Le prisonnier a une chance de s'en sortir "  + ": " + result);
                }else {
                	System.out.println("Le prisonnier n'a pas de chance de s'en sortir"+ ": "+ result) ;
                }
                
            } else {
                System.out.println("Erreur dans la configuration du labyrinthe " + (labyrinths + 1));
            }
        }

        scanner.close();
    }
}