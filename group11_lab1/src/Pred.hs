module Pred (
  Pred,
  cambiar, anyDib, allDib, orP, andP, falla, cambia, andd, orr
) where

import Dibujo

type Pred a = a -> Bool


cambia :: (a -> Dibujo a) -> Pred a -> a -> Dibujo a
cambia f p m | p m  = f m
           | otherwise = figura m

orr :: Float -> Float -> Bool -> Bool -> Bool
orr x y a b = a || b

andd :: Float -> Float -> Bool -> Bool -> Bool
andd x y a b = a && b

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por la figura básica indicada por el segundo argumento.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar p f = foldDib (cambia f p) rotar espejar rot45 apilar juntar encimar


-- Alguna básica satisface el predicado.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib p = foldDib p (|| False) (|| False) (|| False) orr orr (||)

-- Todas las básicas satisfacen el predicado.
allDib :: Pred a -> Dibujo a -> Bool
allDib p = foldDib p (&& True) (&& True) (&& True) andd andd (&&)

-- Los dos predicados se cumplen para el elemento recibido.
andP :: Pred a -> Pred a -> Pred a
andP p q a = p a && q a 

-- Algún predicado se cumple para el elemento recibido.
orP :: Pred a -> Pred a -> Pred a
orP p q a = p a || q a

falla :: Bool
falla = True