require 'test_helper'

class TeamsControllerTest < ActionController::TestCase
  setup do
    @season_participation = season_participations(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:teams)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create season_participation" do
    assert_difference('Team.count') do
      post :create, :season_participation => @season_participation.attributes
    end

    assert_redirected_to season_participation_path(assigns(:season_participation))
  end

  test "should show season_participation" do
    get :show, :id => @season_participation.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @season_participation.to_param
    assert_response :success
  end

  test "should update season_participation" do
    put :update, :id => @season_participation.to_param, :season_participation => @season_participation.attributes
    assert_redirected_to season_participation_path(assigns(:season_participation))
  end

  test "should destroy season_participation" do
    assert_difference('Team.count', -1) do
      delete :destroy, :id => @season_participation.to_param
    end

    assert_redirected_to season_participations_path
  end
end
