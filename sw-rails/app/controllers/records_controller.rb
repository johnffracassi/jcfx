class RecordsController < ApplicationController

  def index
  end

  def runs

    scope = params[:scope]
    order = params[:order]

    puts "************************ scope=#{scope} order=#{order}"

    if scope == 'innings'
      @data = BatsmanInnings.find_all_by_filter(order, params)
      render 'batsman_innings'
    end
  end



end
