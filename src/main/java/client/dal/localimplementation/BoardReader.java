package client.dal.localimplementation;

import client.domain.classes.Pawn;
import client.domain.classes.PlayingField;
import client.domain.classes.Tile;

import java.io.BufferedReader;
import java.io.FileReader;

public class BoardReader {

    private BoardReader(){}

    public static void load(String path, PlayingField playingField) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] para = line.split("\\s+");
                String idString = para[0];

                String color = para[1];
                int x = Integer.parseInt(para[2]);
                int y = Integer.parseInt(para[3]);
                Tile tile = new Tile(idString,color,x,y);
                playingField.addToTileList(tile);

                if(tile.getFullId().charAt(2) == 'P'){

                    playingField.addPawn(new Pawn(tile.getFullId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
