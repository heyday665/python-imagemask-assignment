import io
import os
import sys
import base64
import Image

def readImage(nameOfFile):
	t_count = os.stat(nameOfFile).st_size/2
	with open(nameOfFile, "rb") as img:
		return bytearray(img.read())

def greyValue((incR, incG, incB)):
	totalColor = 0
	totalColor += incR*0.21
	totalColor += incG*0.71
	totalColor += incB*0.07
	return (totalColor)

def main():

	active_Mask =	[[0,-1,0],
					[-1, 4,-1],
					[0, -1,0]]
					
	#active_Mask =	[[1,0,-1],
#					[1, 0,-1],
					#[1, 0,-1]]

	imageBytes = readImage("testingWhy.jpg")			#Open image from file to byte array
	image_Pure = Image.open(io.BytesIO(imageBytes))
	image_Impure = Image.open(io.BytesIO(imageBytes))	#Create an Image object from the byte array

	print image_Pure.getbands()
	print ""

	img_Size = image_Pure.getbbox()
	img_Height = img_Size[3]
	img_Width = img_Size[2]

	print img_Size
	print ""

	print img_Width
	print img_Height
	print ""

	img_TotalPixels = img_Height*img_Width
	print img_TotalPixels

	thing_Get = image_Impure.getpixel
	thing_Put = image_Impure.putpixel
	for y in xrange(0, img_Height):
		for x in xrange(0, img_Width):
			#print x,y
			currPixelr, currPixelg, currPixelb = thing_Get((x,y))
			#print x,y ," : ", currPixelr, ", ", currPixelg, ", ", currPixelb

			#if (currPixelr>50): currPixelr -= 50
			#if (currPixelg>50): currPixelg -= 50
			#if (currPixelb>50): currPixelb -= 50

			greyVal = greyValue((currPixelr, currPixelg, currPixelb))

			thing_Put((x,y),(int(greyVal), int(greyVal), int(greyVal)))

			#thing_Put((x,y),(currPixelr, currPixelg, currPixelb))
			#thing_Put((x,y),(0, 0, 0))

	image_Impure.save("greyscale.jpg") # Write greyscaled image to file, 
									   # incase I break stuff past this point

	tempMatrix = [[0 for x in xrange(img_Width)] for y in xrange(img_Height)]

	for y in xrange(0, img_Height-1):
		for x in xrange(0, img_Width-1):
			tempValue = 0
			greenTrash = 0
			blueTrash = 0
			midMid, greenTrash, blueTrash = thing_Get((x,y))

			if(y==0):
				if (x==0):
					midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midDown * active_Mask[2][1]
					tempValue += rightMid * active_Mask[1][2]
					tempValue += rightDown * active_Mask[2][2]
				elif(0<x<img_Width-1):
					leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
					midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += leftMid * active_Mask[1][0]
					tempValue += leftDown * active_Mask[2][0]
					tempValue += midDown * active_Mask[2][1]
					tempValue += rightMid * active_Mask[1][2]
					tempValue += rightDown * active_Mask[2][2]					
				elif(x==(img_Width-1)):
					leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
					midDown, greenTrash, blueTrash = thing_Get((x,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += leftMid * active_Mask[1][0]
					tempValue += leftDown * active_Mask[2][0]
					tempValue += midDown * active_Mask[2][1]									
			elif(0<y<img_Height-1):
				if (x==0):
					midUp, greenTrash, blueTrash = thing_Get((x,y-1))
					midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					rightUp, greenTrash, blueTrash = thing_Get((x+1,y-1))
					rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
					#leftUp, greenTrash, blueTrash = thing_Get((x-1,y-1))
					#leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					#leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midUp * active_Mask[0][1]
					tempValue += midDown * active_Mask[2][1]
					tempValue += rightUp * active_Mask[0][2]
					tempValue += rightMid * active_Mask[1][2]
					tempValue += rightDown * active_Mask[2][2]
					#tempValue += leftUp * active_Mask[0][0]
					#tempValue += leftMid * active_Mask[1][0]
					#tempValue += leftDown * active_Mask[2][0]					
				elif(0<x<img_Width-1):
					midUp, greenTrash, blueTrash = thing_Get((x,y-1))
					midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					rightUp, greenTrash, blueTrash = thing_Get((x+1,y-1))
					rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
					leftUp, greenTrash, blueTrash = thing_Get((x-1,y-1))
					leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midUp * active_Mask[0][1]
					tempValue += midDown * active_Mask[2][1]
					tempValue += rightUp * active_Mask[0][2]
					tempValue += rightMid * active_Mask[1][2]
					tempValue += rightDown * active_Mask[2][2]
					tempValue += leftUp * active_Mask[0][0]
					tempValue += leftMid * active_Mask[1][0]
					tempValue += leftDown * active_Mask[2][0]
				elif(x==(img_Width-1)):
					midUp, greenTrash, blueTrash = thing_Get((x,y-1))
					midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					#rightUp, greenTrash, blueTrash = thing_Get((x+1,y-1))
					#rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					#rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
					leftUp, greenTrash, blueTrash = thing_Get((x-1,y-1))
					leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midUp * active_Mask[0][1]
					tempValue += midDown * active_Mask[2][1]
					#tempValue += rightUp * active_Mask[0][2]
					#tempValue += rightMid * active_Mask[1][2]
					#tempValue += rightDown * active_Mask[2][2]
					tempValue += leftUp * active_Mask[0][0]
					tempValue += leftMid * active_Mask[1][0]
					tempValue += leftDown * active_Mask[2][0]
			elif(y==(img_Height-1)):
				if (x==0):
					midUp, greenTrash, blueTrash = thing_Get((x,y-1))
					#midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					rightUp, greenTrash, blueTrash = thing_Get((x+1,y-1))
					rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					#rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
					#leftUp, greenTrash, blueTrash = thing_Get((x-1,y-1))
					#leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					#leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midUp * active_Mask[0][1]
					#tempValue += midDown * active_Mask[2][1]
					tempValue += rightUp * active_Mask[0][2]
					tempValue += rightMid * active_Mask[1][2]
					#tempValue += rightDown * active_Mask[2][2]
					#tempValue += leftUp * active_Mask[0][0]
					#tempValue += leftMid * active_Mask[1][0]
					#tempValue += leftDown * active_Mask[2][0]					
				elif(0<x<img_Width-1):
					midUp, greenTrash, blueTrash = thing_Get((x,y-1))
					#midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					rightUp, greenTrash, blueTrash = thing_Get((x+1,y-1))
					rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					#rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
					leftUp, greenTrash, blueTrash = thing_Get((x-1,y-1))
					leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					#leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midUp * active_Mask[0][1]
					#tempValue += midDown * active_Mask[2][1]
					tempValue += rightUp * active_Mask[0][2]
					tempValue += rightMid * active_Mask[1][2]
					#tempValue += rightDown * active_Mask[2][2]
					tempValue += leftUp * active_Mask[0][0]
					tempValue += leftMid * active_Mask[1][0]
					#tempValue += leftDown * active_Mask[2][0]					
				elif(x==(img_Width-1)):
					midUp, greenTrash, blueTrash = thing_Get((x,y-1))
					#midDown, greenTrash, blueTrash = thing_Get((x,y+1))
					#rightUp, greenTrash, blueTrash = thing_Get((x+1,y-1))
					#rightMid, greenTrash, blueTrash = thing_Get((x+1,y))
					#rightDown, greenTrash, blueTrash = thing_Get((x+1,y+1))
					leftUp, greenTrash, blueTrash = thing_Get((x-1,y-1))
					leftMid, greenTrash, blueTrash = thing_Get((x-1,y))
					#leftDown, greenTrash, blueTrash = thing_Get((x-1,y+1))
						#Complete
					tempValue += midMid * active_Mask[1][1]
					tempValue += midUp * active_Mask[0][1]
					#tempValue += midDown * active_Mask[2][1]
					#tempValue += rightUp * active_Mask[0][2]
					#tempValue += rightMid * active_Mask[1][2]
					#tempValue += rightDown * active_Mask[2][2]
					tempValue += leftUp * active_Mask[0][0]
					tempValue += leftMid * active_Mask[1][0]
					#tempValue += leftDown * active_Mask[2][0]

			#print x,y
			tempMatrix[y][x] = tempValue

	for y in xrange(0, img_Height):
		for x in xrange(0, img_Width):
			sendVal = tempMatrix[y][x]
			thing_Put((x,y), (sendVal, sendVal, sendVal))

	image_Impure.save("masked.jpg")						#Write this edited image to file

main()