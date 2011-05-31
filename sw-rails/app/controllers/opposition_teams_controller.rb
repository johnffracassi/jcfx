class OppositionTeamsController < ApplicationController
  # GET /opposition_teams
  # GET /opposition_teams.xml
  def index
    @opposition_teams = OppositionTeam.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @opposition_teams }
    end
  end

  # GET /opposition_teams/1
  # GET /opposition_teams/1.xml
  def show
    @opposition_team = OppositionTeam.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @opposition_team }
    end
  end

  # GET /opposition_teams/new
  # GET /opposition_teams/new.xml
  def new
    @opposition_team = OppositionTeam.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @opposition_team }
    end
  end

  # GET /opposition_teams/1/edit
  def edit
    @opposition_team = OppositionTeam.find(params[:id])
  end

  # POST /opposition_teams
  # POST /opposition_teams.xml
  def create
    @opposition_team = OppositionTeam.new(params[:opposition_team])

    respond_to do |format|
      if @opposition_team.save
        format.html { redirect_to(@opposition_team, :notice => 'Opposition team was successfully created.') }
        format.xml  { render :xml => @opposition_team, :status => :created, :location => @opposition_team }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @opposition_team.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /opposition_teams/1
  # PUT /opposition_teams/1.xml
  def update
    @opposition_team = OppositionTeam.find(params[:id])

    respond_to do |format|
      if @opposition_team.update_attributes(params[:opposition_team])
        format.html { redirect_to(@opposition_team, :notice => 'Opposition team was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @opposition_team.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /opposition_teams/1
  # DELETE /opposition_teams/1.xml
  def destroy
    @opposition_team = OppositionTeam.find(params[:id])
    @opposition_team.destroy

    respond_to do |format|
      format.html { redirect_to(opposition_teams_url) }
      format.xml  { head :ok }
    end
  end
end
