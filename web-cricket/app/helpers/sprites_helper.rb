module SpritesHelper

  def animations_declarations
    concat("var anim = new Array();\n")
    animation_bat_idle
    return
  end

  def animation_bat_idle
    concat("anim['BatIdle'] = generateAnimBatIdle();\n")
    concat("function generateAnimBatIdle() { return [#{frame(0,2,50)}#{frame(0,5,3)}#{frame(0,2,2)}#{frame(0,5,5)}[0,2]]; }")
    return
  end

  def frame(row,col,times)
    "[#{row},#{col}]," * times
  end

end
