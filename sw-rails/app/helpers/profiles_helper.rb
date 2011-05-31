module ProfilesHelper

  def bat_bell(inns)
    arr = []
    inns.sort{|x,y|x.score <=> y.score}.each_with_index{|inns,idx|arr << [idx,inns.score]}
    arr
  end

end
