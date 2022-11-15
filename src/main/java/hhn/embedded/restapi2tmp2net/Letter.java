package hhn.embedded.restapi2tmp2net;

import java.sql.Array;

public class Letter {

    private int width;
    private int[][] letterArray;


    public Letter(int width, int[][] letterArray) {
        this.width = width;
        this.letterArray = letterArray;
    }

    public int[][] getLetterArray() {
        return letterArray;
    }

    public int getWidth() {
        return width;
    }

    public static Letter getLetter(char letter) {
        int[][] letterArray;
        switch (letter) {
            case 'A':
                letterArray = new int[][]{
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 1}
                };
                return new Letter(5, letterArray);
            case 'B':
                letterArray = new int[][]{
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'C':
                letterArray = new int[][]{
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {0, 1, 1, 1}

                };
                return new Letter(4, letterArray);
            case 'D':
                letterArray = new int[][]{
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'E':
                letterArray = new int[][]{
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
                return new Letter(4, letterArray);
            case 'F':
                letterArray = new int[][]{
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
                return new Letter(4, letterArray);
            case 'G':
                letterArray = new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'H':
                letterArray = new int[][]{
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
                return new Letter(4, letterArray);
            case 'I':
                letterArray = new int[][]{
                        {1},
                        {1},
                        {1},
                        {1},
                        {1}
                };
                return new Letter(1, letterArray);
            case 'J':
                letterArray = new int[][]{
                        {0, 0, 0, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'K':
                letterArray = new int[][]{
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {1, 1, 0, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
                return new Letter(4, letterArray);
            case 'L':
                letterArray = new int[][]{
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
                return new Letter(4, letterArray);
            case 'M':
                letterArray = new int[][]{
                        {1, 0, 0, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1}
                };
                return new Letter(5, letterArray);
            case 'N':
                letterArray = new int[][]{
                        {1, 0, 0, 1},
                        {1, 1, 0, 1},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
                return new Letter(4, letterArray);
            case 'O':
                letterArray = new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'P':
                letterArray = new int[][]{
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
                return new Letter(4, letterArray);
            case 'Q':
                letterArray = new int[][]{
                        {0, 1, 1, 1, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 1, 0},
                        {0, 1, 1, 0, 1}
                };
                return new Letter(5, letterArray);
            case 'R':
                letterArray = new int[][]{
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
                return new Letter(4, letterArray);
            case 'S':
                letterArray = new int[][]{
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'T':
                letterArray = new int[][]{
                        {1, 1, 1, 1, 1},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0}
                };
                return new Letter(5, letterArray);
            case 'U':
                letterArray = new int[][]{
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
                return new Letter(4, letterArray);
            case 'V':
                letterArray = new int[][]{
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0}
                };
                return new Letter(5, letterArray);
            case 'W':
                letterArray = new int[][]{
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 1}
                };
                return new Letter(5, letterArray);
            case 'X':
                letterArray = new int[][]{
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {1, 0, 0, 0, 1}
                };
                return new Letter(5, letterArray);
            case 'Y':
                letterArray = new int[][]{
                        {1, 0, 0, 0, 1, 0},
                        {0, 1, 0, 1, 1, 0},
                        {0, 0, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0}
                };
                return new Letter(5, letterArray);
            case 'Z':
                letterArray = new int[][]{
                        {1, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {1, 0, 1, 0},
                        {1, 1, 1, 1}
                };
                return new Letter(4, letterArray);
            case ' ':
                letterArray = new int[][]{
                        {0},
                        {0},
                        {0},
                        {0},
                        {0}
                };
                return new Letter(1, letterArray);

            default:
                letterArray = new int[][]{
                        {0},
                        {0},
                        {0},
                        {0},
                        {0}
                };
                return new Letter(1, letterArray);
        }
    }

    public static int[][] convertText(String message){

        int pos = 0;
        int[][]buchstaben = new int[5][getWidthMessage(message)];
        message = message.toUpperCase();
        char [] letters = message.toCharArray();

        for (char letter:letters) {
            Letter l = Letter.getLetter(letter);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < l.getWidth(); x++) {
                    buchstaben[y][x + pos] = l.getLetterArray()[y][x];
                }
            }
            pos = pos + l.getWidth() + 1;
        }
        return buchstaben;
    }

    public static int getWidthMessage(String message){
        int width = 0;
        message = message.toUpperCase();
        char [] letters = message.toCharArray();
        for (char letter:letters) {
            int widthLetter = Letter.getLetter(letter).getWidth();
            width = width +1 + widthLetter;
        }
        return width;
    }

}



