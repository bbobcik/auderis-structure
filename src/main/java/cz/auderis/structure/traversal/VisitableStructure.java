package cz.auderis.structure.traversal;

public interface VisitableStructure<E> {

    void visit(TraversalStrategy strategy, NodeVisitor<E> visitor);

}
