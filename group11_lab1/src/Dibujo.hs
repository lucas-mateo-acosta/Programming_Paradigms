
module Dibujo (Dibujo, encimar, 
    figura,
    apilar,
    juntar,
    rot45,
    rotar,
    espejar,
    (^^^),
    (.-.),
    (///),
    r180,
    r270,
    encimar4,
    cuarteto,
    ciclar,
    comp,
    mapDib,
    change,
    foldDib
    ) where


-- nuestro lenguaje 
data Dibujo a = Figura a 
              | Rotar (Dibujo a) 
              | Espejar (Dibujo a) 
              | Rot45 (Dibujo a)
              | Apilar Float Float (Dibujo a) (Dibujo a)
              | Juntar Float Float (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
              deriving (Show, Eq)

-- combinador
infixr 6 ^^^  -- p ^^^ q  --> se van a superponer p y q

infixr 7 .-.  -- Apilar. El primero lo pone arriba del otro. La relacion de espacio es 1:1. Ej. p .-. q

infixr 8 ///  -- Juntar. Pone un dibujo al lado del otro



comp :: Int -> (a -> a) -> a -> a
comp 0 _ a = a
comp n f a = f (comp (n-1) f a)

-- Funciones constructoras
figura :: a -> Dibujo a
figura a = Figura a -- se puede agregar el argumento "a" o no

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar  -- Ejemplo de uso ghci encimar (figura 1) (figura 2) --> Encimar (Figura 1) (Figura 2)

apilar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar = Apilar

juntar  :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

rot45 :: Dibujo a -> Dibujo a
rot45 = Rot45

rotar :: Dibujo a -> Dibujo a
rotar = Rotar


espejar :: Dibujo a -> Dibujo a
espejar = Espejar

(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = encimar

(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = apilar 1 1

(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = juntar 1 1

-- rotaciones

r180 :: Dibujo a -> Dibujo a
r180 = comp 2 rotar

r270 :: Dibujo a -> Dibujo a
r270 = comp 3 rotar

-- una figura repetida con las cuatro rotaciones, superimpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 dib = dib ^^^ rotar dib ^^^ r180 dib ^^^ r270 dib

-- cuatro figuras en un cuadrante.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto a b c d = (a /// b) .-. (c /// d)

-- Cuadrado con el mismo dibujo rotado i * 90, para i ∈ {0, ..., 3}.
-- No confundir con encimar4!

ciclar :: Dibujo a -> Dibujo a
ciclar dib = cuarteto dib (rotar dib) (r180 dib) (r270 dib)

-- map para nuestro lenguaje
mapDib :: (a ->b) -> Dibujo a -> Dibujo b
mapDib f (Figura a) = Figura (f a)
mapDib f (Rotar a) = Rotar (mapDib f a)
mapDib f (Espejar a) = Espejar (mapDib f a)
mapDib f (Rot45 a) = Rot45 (mapDib f a)
mapDib f (Apilar n m a b) = Apilar n m (mapDib f a) (mapDib f b)
mapDib f (Juntar n m a b) = Juntar n m (mapDib f a) (mapDib f b)
mapDib f (Encimar a b) = Encimar (mapDib f a) (mapDib f b)
-- verificar que las operaciones satisfagan
-- 1. map figura = id
-- 2. map (g . f) = mapDib g . mapDib f

-- Cambiar todas las básicas de acuerdo a la función.
change :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
change f (Figura a) = f a
change f (Rotar a) = Rotar (change f a)
change f (Espejar a) = Espejar (change f a)
change f (Rot45 a) = Rot45 (change f a)
change f (Apilar n m a b) = Apilar n m (change f a) (change f b)
change f (Juntar n m a b) = Juntar n m (change f a) (change f b)
change f (Encimar a b) = Encimar (change f a) (change f b)
-- no entiendo bien change que hace ?  

-- Principio de recursión para Dibujos.
foldDib ::
  (a -> b) ->
  (b -> b) ->
  (b -> b) ->
  (b -> b) ->
  (Float -> Float -> b -> b -> b) ->
  (Float -> Float -> b -> b -> b) ->
  (b -> b -> b) ->
  Dibujo a ->
  b

foldDib fFigura _ _ _ _ _ _ (Figura a) = fFigura a
foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc (Rotar a) = fRotar (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc a)
foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc (Espejar a) = fEsp (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc a)
foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc (Rot45 a) = fRotar45 (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc a)
foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc (Apilar x y dib1 dib2) = fApi x y (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc dib1)
                                                                                      (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc dib2)
foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc (Juntar x y dib1 dib2) = fJun x y (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc dib1)
                                                                                      (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc dib2)
foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc (Encimar dib1 dib2) = fEnc (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc dib1)
                                                                               (foldDib fFigura fRotar fEsp fRotar45 fApi fJun fEnc dib2)