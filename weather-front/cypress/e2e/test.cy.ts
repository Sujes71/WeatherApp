describe('WeatherApp Test', () => {
  beforeEach(() =>{
    cy.visit('/')
  })
  const assertDataIsShown = () => {
    cy.contains('Marbella').should('be.visible');
    cy.get('[data-cy="forecast"] .name').should('be.visible');
    cy.get('[data-cy="forecast"] .date').should('be.visible');
    cy.get('[data-cy="forecast"] .temp').should('be.visible');
    cy.get('[data-cy="value"]').should('have.length.gt', 0);
    cy.get('[data-cy="forecast"] .temp').contains("ºF")
  }

  it('is working fine?', () => {
    cy.title().should('equal', 'WeatherApp');
    cy.get('[data-cy=inputName]').click();
    cy.wait(2000)
    cy.get('[data-cy=inputName]').type('Marbella');
    cy.wait(2000)
    cy.get('[data-cy=mat-option]').first().click();
    cy.wait(1500)
    cy.get('[data-cy=select]').select(1);
    cy.wait(1500)
    assertDataIsShown();

    let lastAvg: number;

    cy.get('[data-cy="forecast"] .temp').invoke("text").then(avg => {
      lastAvg = parseInt(avg); 
    })
    cy.get('[data-cy=select]').select(0);
    cy.wait(2000)
    cy.get('[data-cy="forecast"] .temp').contains("ºC")
    cy.get('[data-cy="forecast"] .temp').invoke("text").then(avg => {
      const newAvg: number = parseInt(avg);

      expect(lastAvg).to.be.greaterThan(newAvg);
    })
  });
})

