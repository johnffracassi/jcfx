package com.siebentag.cj.mvc;

import java.util.List;

import com.siebentag.cj.util.math.Point3D;

public class SceneObject
{
	String name;
	Point3D location;
	Point3D direction;

	SceneObject parent;
	List<SceneObject> children;

	int frameIndex;
}