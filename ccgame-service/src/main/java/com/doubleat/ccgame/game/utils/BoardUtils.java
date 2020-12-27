package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.piece.Piece;

public final class BoardUtils {

    public BoardUtils() {
    }

    public static String convertBoardToString(Piece[][] pieces) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COLUMN; j++) {
                Piece piece = pieces[i][j];
                if (piece != null) {
                    String color = piece.isRed() ? "r" : "b";
                    res.append(i).append(j).append(color).append(piece.getShortName());
                } else {
                    res.append(i).append(j).append("000");
                }
                // Separate
                if (i != Board.ROW - 1 || j != Board.COLUMN - 1) {
                    res.append("_");
                }
            }
        }
        return res.toString();
    }

    public static Piece[][] convertBoardToMatrix(String boardStatus) {
        Piece[][] pieces = new Piece[Board.ROW][Board.COLUMN];

        String[] temp = boardStatus.split("_");
        for (String pieceText : temp) {
            char color = pieceText.charAt(2);
            int x = pieceText.charAt(0) - '0';
            int y = pieceText.charAt(1) - '0';
            if (color != '0') {
                String shortName = pieceText.substring(3);
                boolean isRed = (color == 'r');

                Piece piece = PieceUtils.getPieceInstanceFromShortName(shortName);
                piece.setRed(isRed);

                pieces[x][y] = piece;
            } else {
                pieces[x][y] = null;
            }
        }
        return pieces;
    }

}
