class PlayerAliasesController < ApplicationController
  # GET /player_aliases
  # GET /player_aliases.xml
  def index
    @player_aliases = PlayerAlias.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @player_aliases }
    end
  end

  # GET /player_aliases/1
  # GET /player_aliases/1.xml
  def show
    @player_alias = PlayerAlias.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @player_alias }
    end
  end

  # GET /player_aliases/new
  # GET /player_aliases/new.xml
  def new
    @player_alias = PlayerAlias.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @player_alias }
    end
  end

  # GET /player_aliases/1/edit
  def edit
    @player_alias = PlayerAlias.find(params[:id])
  end

  # POST /player_aliases
  # POST /player_aliases.xml
  def create
    @player_alias = PlayerAlias.new(params[:player_alias])

    respond_to do |format|
      if @player_alias.save
        format.html { redirect_to(@player_alias, :notice => 'Player alias was successfully created.') }
        format.xml  { render :xml => @player_alias, :status => :created, :location => @player_alias }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @player_alias.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /player_aliases/1
  # PUT /player_aliases/1.xml
  def update
    @player_alias = PlayerAlias.find(params[:id])

    respond_to do |format|
      if @player_alias.update_attributes(params[:player_alias])
        format.html { redirect_to(@player_alias, :notice => 'Player alias was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @player_alias.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /player_aliases/1
  # DELETE /player_aliases/1.xml
  def destroy
    @player_alias = PlayerAlias.find(params[:id])
    @player_alias.destroy

    respond_to do |format|
      format.html { redirect_to(player_aliases_url) }
      format.xml  { head :ok }
    end
  end
end
