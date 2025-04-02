package org.juan.dragonblockbr.item;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.*;
import net.minecraftforge.registries.DeferredRegister;

import java.io.*;
import java.util.List;

public class FerramentasLoader {

    public static void carregarFerramentas(DeferredRegister<Item> itens) {
        try {
            File file = new File("C:\\Users\\zx21n\\Downloads\\Forge\\Forge-Tutorial-1.16.5\\src\\main\\resources\\data\\examplemod\\ferramentas.json");
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            List<FerramentaData> ferramentasData = gson.fromJson(reader, new TypeToken<List<FerramentaData>>() {}.getType());



            for (FerramentaData data : ferramentasData) {
                Item.Properties propriedades = new Item.Properties()
                        .durability(data.durabilidade);

                IItemTier tier = getTier(data.tier);
                switch (data.tipo) {


                    case "espada":
                        itens.register(data.nome, () -> new SwordItem(tier, data.dano, data.velocidade_ataque, propriedades));
                        break;
                    case "picareta":
                        itens.register(data.nome, () -> new PickaxeItem(tier, data.dano, data.velocidade_ataque, propriedades));
                        break;
                    case "pa":
                        itens.register(data.nome, () -> new ShovelItem(tier, data.dano, data.velocidade_ataque, propriedades));
                        break;
                    case "machado":
                        itens.register(data.nome, () -> new AxeItem(tier, data.dano, data.velocidade_ataque, propriedades));
                        break;
                    case "enxada":
                        itens.register(data.nome, () -> new HoeItem(tier, data.dano, data.velocidade_ataque, propriedades));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static IItemTier getTier(String tierName) {
        switch (tierName) {
            case "DIAMOND":
                return ItemTier.DIAMOND; // Tier do Vanilla
            case "IRON":
                return ItemTier.IRON; // Tier do Vanilla
            case "GOLD":
                return ItemTier.GOLD; // Tier do Vanilla
            case "STONE":
                return ItemTier.STONE; // Tier do Vanilla
            default:
                return ItemTier.WOOD; // Padrão (caso o tier não seja reconhecido)
        }
    }

    // Classe para representar os dados da ferramenta
    private static class FerramentaData {
        String nome;
        String tipo;
        int durabilidade;
        int dano;
        String tier;
        float velocidade_ataque;
    }
}
