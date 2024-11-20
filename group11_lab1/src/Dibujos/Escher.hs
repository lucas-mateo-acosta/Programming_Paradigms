module Dibujos.Escher where

import Dibujo (Dibujo, figura, juntar, apilar, rot45, rotar,r180,r270, espejar, cuarteto, (^^^))
import FloatingPic(Conf(..), Output)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Graphics.Gloss ( line, color, withAlpha, blue)


data Triangulos =  Triangulo | Vacio deriving(Show, Eq)

type Escher = Triangulos


interpTrng :: Output Escher
interpTrng Triangulo x y w = line $ map (x V.+) [(0,0), y , w, (0,0)]
interpTrng Vacio x y w = color (withAlpha 0 blue) (line [x, x V.+ y, x V.+ y V.+ w, x V.+ w, x])


triangulo1 :: Dibujo Escher -> Dibujo Escher
triangulo1 p = espejar (rot45 p)

triangulo2 :: Dibujo Escher -> Dibujo Escher
triangulo2 p = r270 (triangulo1 p)

-- El dibujo u.
dibujoU :: Dibujo Escher -> Dibujo Escher
dibujoU p = (triangulo1 p ^^^ rotar (triangulo1 p)) ^^^ r180 (triangulo1 p) ^^^ triangulo2 p

-- El dibujo t.
dibujoT :: Dibujo Escher -> Dibujo Escher
dibujoT p = p ^^^ (triangulo1 p ^^^ triangulo2 p)


-- Esquina con nivel de detalle en base a la figura p.
esquina :: Int -> Dibujo Escher -> Dibujo Escher
esquina 1 p = cuarteto (figura Vacio) (figura Vacio) (figura Vacio) (dibujoU p)
esquina n p = cuarteto (esquina (n-1) p) (lado (n-1) p) (rotar (lado (n-1) p)) (dibujoU p)

-- Lado con nivel de detalle.
lado :: Int -> Dibujo Escher -> Dibujo Escher
lado 1 p = cuarteto (figura Vacio) (figura Vacio) (rotar (dibujoT p)) (dibujoT p) 
lado n p = cuarteto (lado (n-1) p) (lado (n-1) p) (rotar (dibujoT p)) (dibujoT p)

-- Por suerte no tenemos que poner el tipo!
noneto p q r s t u v w x = apilar 1 2 (juntar 1 2 p (juntar 1 1 q r)) 
                                      (apilar 1 1 (juntar 1 2 s (juntar 1 1 t u)) 
                                                  (juntar 1 2 v (juntar 1 1 w x)))


-- El dibujo de Escher:
escher :: Int -> Escher -> Dibujo Escher
escher n e = noneto (esquina n (figura e)) (lado n (figura e)) (r270 (esquina n (figura e)))
                    (rotar (lado n (figura e))) (dibujoU (figura e)) (r270 (lado n (figura e)))
                    (rotar (esquina n (figura e))) (r180 (lado n (figura e))) (r180 (esquina n (figura e)))

row :: [Dibujo a] -> Dibujo a
row [] = error "row: no puede ser vacío"
row [d] = d
row (d:ds) = juntar 1 (fromIntegral $ length ds) d (row ds)

column :: [Dibujo a] -> Dibujo a
column [] = error "column: no puede ser vacío"
column [d] = d
column (d:ds) = apilar 1 (fromIntegral $ length ds) d (column ds)

grilla :: [[Dibujo a]] -> Dibujo a
grilla = column . map row

testAll :: Dibujo Escher
testAll = grilla [
    [escher 2 Triangulo]
    ]

escherConf :: Conf
escherConf = Conf {
    name = "Escher"
    , pic = testAll
    , bas = interpTrng
}