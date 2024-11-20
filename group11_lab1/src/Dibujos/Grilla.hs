module Dibujos.Grilla where
    
import Dibujo (Dibujo, juntar, apilar, figura)
import FloatingPic(Conf(..), Output)
import Graphics.Gloss ( Picture, pictures, text, scale, translate )

type Tupla = (Float, Float)


--tuplePic :: Tupla -> Picture
--tuplePic (x,y) = text $ show (x,y)


interpTup :: Output Tupla
interpTup (x,y) _ _ _ = translate (y*100+20) (750-(x*100)) (scale 0.1 0.1 (text $ show (round x, round y)))  

-- x, y son los valores de las tuplas que se muestran por pantalla. z, v son las coordenadas en el plano (z seria en x, v seria en y)
--dibujos :: Picture
--dibujos = pictures [translate z v (scale 0.1 0.1 (tuplePic (x,y))) | (v, x) <- zip [750, 650..50] [0..7], (z, y) <- zip [20, 120..720] [0..7]] 

row :: [Dibujo a] -> Dibujo a
row [] = error "row: no puede ser vacío"
row [d] = d
row (d:ds) = juntar (fromIntegral $ length ds) 1 d (row ds)

column :: [Dibujo a] -> Dibujo a
column [] = error "column: no puede ser vacío"
column [d] = d
column (d:ds) = apilar (fromIntegral $ length ds) 1 d (column ds)

grilla :: [[Dibujo a]] -> Dibujo a
grilla = column . map row

testAll :: Dibujo Tupla
testAll = grilla [
    [figura (x,y) | x <- [0.0 .. 7.0], y <- [0.0 .. 7.0]]
    ]

grillaConf :: Conf
grillaConf = Conf {
    name = "Grilla"
    , pic = testAll
    , bas = interpTup
}
