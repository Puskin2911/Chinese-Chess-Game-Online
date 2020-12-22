package com.doubleat.ccgame.game;

import com.doubleat.ccgame.game.piece.*;
import com.doubleat.ccgame.game.utils.PieceUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    public static final int COLUMN = 9;
    public static final int ROW = 10;

    @Getter(AccessLevel.NONE)
    private String defaultBoardStatus = "00bch_01bho_02bel_03bad_04bge_05bad_06bel_07bho_08bch"
            + "_10000_11000_12000_13000_14000_15000_16000_17000_18000"
            + "_20000_21bca_22000_23000_24000_25000_26000_27bca_28000"
            + "_30bso_31000_32bso_33000_34bso_35000_36bso_37000_38bso"
            + "_40000_41000_42000_43000_44000_45000_46000_47000_48000"
            + "_50000_51000_52000_53000_54000_55000_56000_57000_58000"
            + "_60rso_61000_62rso_63000_64rso_65000_66rso_67000_68rso"
            + "_70000_71rca_72000_73000_74000_75000_76000_77rca_78000"
            + "_80000_81000_82000_83000_84000_85000_86000_87000_88000"
            + "_90rch_91rho_92rel_93rad_94rge_95rad_96rel_97rho_98rch";
    private Piece[][] pieces;

    /**
     * Initial a new board with default board.
     */
    public Board() {
        pieces = convertToMatrix(defaultBoardStatus);
    }

    public Piece getPieceByPosition(Position position) {
        assert position != null;

        return pieces[position.getX()][position.getY()];
    }

    public static Piece[][] convertToMatrix(String boardStatus) {
        Piece[][] pieces = new Piece[Board.ROW][Board.COLUMN];

        String[] temp = boardStatus.split("_");
        for (String pieceText : temp) {
            char color = pieceText.charAt(2);
            int x = pieceText.charAt(0) - '0';
            int y = pieceText.charAt(1) - '0';
            if (color != '0') {
                String shortName = pieceText.substring(3);
                boolean isRed = (color == 'r');

                Piece piece = PieceUtils.getInstancePieceFromShortName(shortName);
                piece.setRed(isRed);

                pieces[x][y] = piece;
            } else {
                pieces[x][y] = null;
            }
        }
        return pieces;
    }

    public String getBoardStatus() {
        return convertBoardToString(pieces);
    }

    private static String convertBoardToString(Piece[][] pieces) {
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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Board.ROW; i++) {
            if (i == 5)
                res.append("==========================\n");
            for (int j = 0; j < Board.COLUMN; j++) {
                Piece piece = pieces[i][j];
                if (piece == null) {
                    res.append("00 ");
                } else {
                    res.append(piece.getShortName()).append(" ");
                }
            }
            res.append("\n");
        }
        return res.toString();
    }

}
