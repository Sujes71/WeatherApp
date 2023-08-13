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
    cy.get('[data-cy="forecast"] .temp').contains("ºF");
  }

  it('is working fine?', () => {
    cy.title().should('equal', 'WeatherApp');
    cy.get('[data-cy=inputName]').click();
    cy.wait(3000);
    cy.get('[data-cy=inputName]').type('Marbella');
    cy.wait(2000);
    cy.get('[data-cy=mat-option]').first().click();
    cy.wait(1500);
    cy.get('[data-cy=select]').select(1);
    cy.wait(1500);
    assertDataIsShown();

    let lastAvg: number;

    cy.get('[data-cy="forecast"] .temp').invoke('text').then(avg => {
      lastAvg = parseInt(avg); 
    })
    cy.get('[data-cy=select]').select(0);
    cy.wait(2000);
    cy.get('[data-cy="forecast"] .temp').contains('ºC');
    cy.get('[data-cy="forecast"] .temp').invoke('text').then(avg => {
      const newAvg: number = parseInt(avg);

      expect(lastAvg).to.be.greaterThan(newAvg);
    })
    cy.get('[data-cy=saveButton]').click();
    cy.wait(1000);
    cy.get('div[role=tab]').eq(1).click();
    cy.wait(1000);
    cy.get('[data-cy=mat-input]').click();
    cy.wait(500);
    cy.get('[data-cy=mat-input]').type('Marbella');
    cy.wait(1000);

    let iniMarbellaCount: number = 0;
    let finalMarbellaCount: number = 0;
    cy.get('[data-cy="mat-cell"]').each((cell) => {
      expect(cell).to.have.text(' Marbella ');
      iniMarbellaCount += 1;
    });    
    console.log(iniMarbellaCount);
    cy.wait(1000);
    cy.get('[data-cy=delete]').click();
    cy.wait(1000);
    cy.get('[data-cy=confirm]').click();
    cy.wait(1000);
    
    cy.get('[data-cy="mat-cell"]').should($el => {
      const doesNotExist = $el.length == 0;
      const isNotVisible = !$el.is("visible");
      const doesNotExistOrIsNotVisible = doesNotExist || isNotVisible;
      expect(doesNotExistOrIsNotVisible, "does not exist or is not visible").to.be
    .true;
    if (!doesNotExistOrIsNotVisible) {
      cy.get('[data-cy="mat-cell"]').each((cell) => {
        if (cell.text().includes(' Marbella ')) {
          finalMarbellaCount++;
        }
      });
    }
    }).then(() => {
      cy.wrap(finalMarbellaCount).should('be.lt', iniMarbellaCount);
    });
  });
})

