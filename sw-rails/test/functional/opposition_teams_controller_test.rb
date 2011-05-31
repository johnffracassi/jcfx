require 'test_helper'

class OppositionTeamsControllerTest < ActionController::TestCase
  setup do
    @opposition_team = opposition_teams(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:opposition_teams)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create opposition_team" do
    assert_difference('OppositionTeam.count') do
      post :create, :opposition_team => @opposition_team.attributes
    end

    assert_redirected_to opposition_team_path(assigns(:opposition_team))
  end

  test "should show opposition_team" do
    get :show, :id => @opposition_team.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @opposition_team.to_param
    assert_response :success
  end

  test "should update opposition_team" do
    put :update, :id => @opposition_team.to_param, :opposition_team => @opposition_team.attributes
    assert_redirected_to opposition_team_path(assigns(:opposition_team))
  end

  test "should destroy opposition_team" do
    assert_difference('OppositionTeam.count', -1) do
      delete :destroy, :id => @opposition_team.to_param
    end

    assert_redirected_to opposition_teams_path
  end
end
