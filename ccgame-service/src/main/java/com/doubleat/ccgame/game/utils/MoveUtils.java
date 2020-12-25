package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.piece.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public final class MoveUtils {

    private static final Logger logger = LoggerFactory.getLogger(MoveUtils.class);

    private MoveUtils() {
    }

    /**
     * Check if move is valid.
     *
     * @param move move need to check.
     * @return {@code true} if the move is valid, other wise return {@code false}.
     */
    public static boolean isValidMove(String move) {
        if (move != null && move.length() == 5) {
            Pattern pattern = Pattern.compile("[0-9][0-8]_[0-9][0-8]");

            return pattern.matcher(move).matches();
        }

        return false;
    }

    public static boolean isHaveValidMove(Board board, Position currentPosition) {
        Piece currentPiece = board.getPieceByPosition(currentPosition);

        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COLUMN; j++) {
                Position toPosition = new Position(i, j);
                if (currentPiece.isValidMove(board, currentPosition, toPosition)) {
                    Board mockBoard = board.clone();
                    Piece[][] mockPieces = mockBoard.getPieces();
                    mockPieces[i][j] = currentPiece.clone();
                    mockPieces[currentPosition.getX()][currentPosition.getY()] = null;

                    Position myGeneralPosition = PieceUtils.findMyGeneralPosition(mockBoard, currentPiece.isRed());
                    if (!isAbleDead(mockBoard, myGeneralPosition)) {
                        logger.info("Can Move this\n" + mockBoard + currentPosition + toPosition);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isAbleDead(Board board, Position currentPosition) {
        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COLUMN; j++) {
                Piece piece = board.getPieces()[i][j];
                if (piece != null && piece.isValidMove(board, new Position(i, j), currentPosition))
                    return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setPieces(Board.convertToMatrix(
                "00000_01000_02000_03000_04bge_05bad_06bch_07000_08000_"
                        + "10000_11000_12000_13000_14bad_15000_16000_17000_18000_"
                        + "20bel_21000_22bho_23000_24bca_25000_26000_27bca_28000_"
                        + "30bso_31000_32bso_33000_34000_35000_36000_37000_38bso_"
                        + "40000_41000_42000_43000_44000_45000_46000_47000_48000_"
                        + "50000_51000_52000_53000_54000_55000_56000_57000_58000_"
                        + "60rso_61000_62rso_63000_64rso_65000_66rso_67000_68rso_"
                        + "70000_71000_72bho_73000_74000_75bch_76000_77000_78rel_"
                        + "80000_81000_82000_83000_84000_85000_86000_87000_88000_"
                        + "90rch_91rho_92000_93000_94000_95rge_96000_97000_98000"));

        boolean is = MoveUtils.isHaveValidMove(board, new Position(0, 4));
        boolean ableDead = isAbleDead(board, new Position(9, 5));
        System.out.println(is);
        System.out.println("Able dead: " + ableDead);
    }

}