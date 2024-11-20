module Interp
  ( interp,
    initial,
  )
where

import Dibujo
import FloatingPic
import Graphics.Gloss (Display (InWindow), color, display, makeColorI, pictures, translate, white, Picture, Vector)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

-- Picture: link a la documentación

--https://hackage.haskell.org/package/gloss-1.13.2.2/docs/Graphics-Gloss-Data-Picture.html

-- Dada una computación que construye una configuración, mostramos por
-- pantalla la figura de la misma de acuerdo a la interpretación para
-- las figuras básicas. Permitimos una computación para poder leer
-- archivos, tomar argumentos, etc.
initial :: Conf -> Float -> IO ()
initial (Conf n dib intBas) size = display win white $ withGrid fig size
  where
    win = InWindow n (ceiling size, ceiling size) (0, 0)
    fig = interp intBas dib (0, 0) (size, 0) (0, size)
    desp = -(size / 2)
    withGrid p x = translate desp desp $ pictures [p, color grey $ grid (ceiling $ size / 10) (0, 0) x 10]
    grey = makeColorI 100 100 100 100

-- Interpretación de (^^^)

ov :: Picture -> Picture -> Picture
ov p q = pictures[p,q]

r45 :: FloatingPic -> FloatingPic 
r45 f d w h = f (d V.+ half (w V.+ h)) (half (w V.+ h)) (half (h V.- w)) 
 -- usamos half de FloatingPic.hs

rot :: FloatingPic -> FloatingPic
rot f d w h =  f (d V.+ w) h (V.negate w)


esp :: FloatingPic -> FloatingPic
esp f d w h = f (d V.+ w) (V.negate w) h

sup :: FloatingPic -> FloatingPic -> FloatingPic
sup f g d w h = pictures[f d w h, g d w h] 

--funcion axiliar
prodVect :: Float -> Vector -> Vector
prodVect l v = l V.* v


jun :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
jun m n f g d w h = pictures[f d w' h, g (d V.+ w') (r' V.* w) h]
    where   
    r  = m/(m+n)
    r' = n/(m+n)
    w' = prodVect r w 

api :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
api  m n f g d w h = pictures[f (d V.+ h') w (prodVect r h), g d w h' ]
    where
    r  = m/(m+n)
    r' = n/(m+n)
    h' = prodVect r' h

interp :: Output a -> Output (Dibujo a)
interp b  = foldDib b rot esp r45 api jun sup

