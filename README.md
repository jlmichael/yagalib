yagalib
=======

Yet Another Genetic Algorithm library - A simple set of Java interfaces and classes to build GA applications

Overview
========

yagalib is a set of interfaces for 4 key operators in a genetic algorithm (GA) framework: Environments, Organisms,
Genomes and Genes.  The library also provides an EvolutionManager class that executes all of the evolutionary
logic against the Environment.

Building
========

yagalib uses Maven for dependency management and building, so you will need a working Maven setup on your box.  To
build, just do "mvn install" in the project root.  This will install the resulting jar in your maven repo.

Usage
=====

The first step to using yagalib for your GA application is to identify the entities representing the 4 interfaces in
your project.

- Environment: The Environment is the world in which the entities you are trying to evolve operate within.  In the
example project included in the yagalib_example module, the class implementing Environment is the Casino class.  The
Casino class includes all the necessary logic and objects to exercise the blackjack-playing Agents that live within
it.  Another example might be a stock market floor.  Environments can also be something more abstract; they exist
for the purpose of aggregating Organisms and forcing each one to exercise its Genes.  Whichever class in your model
handles those functions is a good candidate for implementing the Environment interface.

- Organism: The Organisms are the entities you are trying to evolve to higher levels of fitness.  In the example
provided, the Agent class implements Organism as a blackjack player in a casino.  In a stock market example, the
Organisms might be stock traders.  More abstract Organisms might be things like equations in a line fitting application.
Importantly, the Organism class is where you implement your crucial fitness function, getFitness().  This means that
each Organism must be able to evaluate its own fitness.

- Genome: Each Organism stores its genetic code in a Genome.  A Genome is a collection of Genes, and represent the
"ruleset" each Organism follows in order to make decisions within the Environment.  In the included blackjack example,
the Strategy class implements the Genome interface.  Other examples might be the list of rules a stock broker uses
to buy or sell stocks, etc.  The Genome is responsible for carrying out the Crossover, Complexify and Simplify
genetic operations.

- Gene: Finally, Genes represent the individual rules your Organisms use to make decisions and act in the Environment.
Genes implement the Mutation genetic operation, so you need to make sure that your implementing class is able to
represent its logic as discrete parts of a whole.  A good example from the blackjack example project is the Rule class.
Each Rule is made up of 5 parts: the dealer's up card, the value of the player's hand, whether the hand is hard or soft,
whether the hand is splittable, and finally the command to execute.  When an Agent is prompted for input from the
Dealer, it uses the current up card and its hand to find a Rule in its Genome that matches.  If it finds one, it tells
the Dealer the associated command, which the Dealer executes.  With this encoding of the blackjack player's logic,
mutations are straightforward: choose one of the 5 parts (call them "base pairs" I guess?) of the Gene and change its
value to a random, valid value.

Once you have identified and implemented each of the 4 interfaces in your model, the next step is to instantiate an
EvolutionManager object, set its various rates, and tell it to evolve your Organisms.  The various rates that affect
the pace and efficacy of evolution are:

- Death Rate: how many of the least fit Organisms get culled from the herd each generation, per thousand.
- Birth Rate: how many of the surviving Organisms get to breed offspring each generation, per thousand.
- Mutation Rate: how many of the surviving Organisms undergo a point mutation each generation, per thousand.
- Complexify Rate: how many surviving Organisms spontaneously comlexify their Genomes each generation, per thousand.
- Simplify Rate: how many surviving Organisms spontaneously simplify their Genomes each generatoin, per thousand.

The included blackjack example contains a test case, GABlackJackTest, that illustrates how to setup and execute a run:

>        EvolutionManager em = new EvolutionManager();
>        em.setPopulation(100);
>        em.setBirthRatePerThousand(500);
>        em.setDeathRatePerThousand(500);
>        em.setMutationChancePerThousand(500);
>        em.setComplexifyChancePerThousand(500);
>        em.setSimplifyChancePerThousand(0);
>        em.setEnvironment(new Casino());
>        em.setupEnvironment();
>        FileStatWriter writer = new FileStatWriter("/tmp/genstats");
>        writer.init();
>        em.setWriter(writer);
>        em.evolve(100);

TL;DR
=====

Clone the project, find and execute the GABlackJackTest.  Watch the median fitness of the blackjack players grow.
