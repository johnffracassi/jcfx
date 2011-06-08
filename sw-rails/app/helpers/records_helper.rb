module RecordsHelper

  def order_select(name, order_for_best)
    options = [["Best -> Worst", order_for_best],["Worst -> Best", (order_for_best == 'desc') ? 'asc' : 'desc']]
    select_tag(name, options_for_select(options))
  end

  def season_select(name)
    select_tag(name, options_for_select([['- Select Season -','-1']] + Team.all.collect{|t|t.season}.collect{|s|[s.name,s.id]}))
  end

  def player_select(name)
    select_tag(name, options_for_select([['- Select Player -','-1']] + Player.all.sort{|x,y|x.name <=> y.name}.collect{|p|[p.display,p.id]}))
  end

end
