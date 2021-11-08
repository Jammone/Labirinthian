package it.labirinthian.labirinthian;

public interface IGenerator {
    public Maze.WriteableMaze generate(int width, int height);
}