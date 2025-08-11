package com.github.navelogic.estudiovirtualapi.Engines.MoneyTransferEngine.Enum;

/**
 * Defines the specific business categories for a financial transaction.
 *
 * <p>This enum provides a granular classification for financial events, allowing for
 * detailed reporting, filtering, and application of specific game logic. For example,
 * the system can calculate a production's total profit by summing all its
 * revenue-related categories and subtracting all its expense-related categories.</p>
 *
 * @author Navelogic
 * @since 2025-08-05
 */
public enum TransactionCategoryEnum {

    /**
     * Funds allocated to or spent from the budget of a specific production.
     * (e.g., development costs, asset purchases).
     */
    PRODUCTION_BUDGET,

    /**
     * Expenses related to marketing and promoting a production.
     */
    MARKETING_EXPENSE,

    /**
     * General day-to-day running costs of the studio not tied to a specific production
     * (e.g., rent, utilities, software licenses).
     */
    OPERATIONAL_COSTS,

    /**
     * Revenue generated from theatrical releases of a production (e.g., movie tickets).
     */
    BOX_OFFICE_REVENUE,

    /**
     * Revenue generated from streaming rights or platform releases.
     */
    STREAMING_REVENUE,

    /**
     * Revenue generated from the sale of merchandise related to a production.
     */
    MERCHANDISING_REVENUE,

    /**
     * Funds received from an external investor or from the player investing
     * their personal money into a studio.
     */
    INVESTMENT,

    /**
     * Funds received as a loan, which typically implies a future repayment obligation.
     */
    LOAN,

    /**
     * Costs associated with hiring new crew members (e.g., agency fees, signing bonuses).
     */
    RECRUITMENT,

    /**
     * A general category for income that doesn't fit into the more specific revenue types.
     */
    REVENUE,

    /**
     * Payments made to crew members for their work.
     */
    SALARIES,

    /**
     * A gift of money between entities with no expectation of repayment.
     */
    GIFT,

    /**
     * Expenses incurred after principal photography
     * (e.g., editing, sound mixing, visual effects, color correction).
     */
    POST_PRODUCTION,

    /**
     * Costs related to the actual filming/recording process
     * (e.g., equipment rental, location fees, catering).
     */
    FILMING_EXPENSE,

    /**
     * Costs for acquiring music, stock footage, or other licensed content.
     */
    LICENSING_COSTS,

    /**
     * Costs for distributing content to theaters, streaming platforms, or other channels.
     */
    DISTRIBUTION_COSTS,

    /**
     * Insurance costs for productions, equipment, and key personnel.
     */
    INSURANCE_COSTS,

    /**
     * Travel and accommodation expenses for cast and crew.
     */
    TRAVEL_EXPENSES,

    /**
     * Costs for script development, rewrites, and story rights acquisition.
     */
    SCRIPT_DEVELOPMENT,

    /**
     * Expenses for testing content with focus groups or beta audiences.
     */
    MARKET_RESEARCH,

    /**
     * Major purchases of equipment, facilities, or long-term assets.
     */
    CAPITAL_EXPENDITURE,

    /**
     * Legal fees, accounting services, consulting, and other professional services.
     */
    PROFESSIONAL_SERVICES,

    /**
     * Government taxes, licensing fees, and regulatory compliance costs.
     */
    TAXES_AND_FEES,

    /**
     * Bank charges, transaction fees, and other financial service costs.
     */
    BANK_FEES,

    /**
     * Revenue from sponsorship deals and brand partnerships.
     */
    SPONSORSHIP_REVENUE,

    /**
     * Revenue from TV broadcast rights, both domestic and international.
     */
    BROADCAST_RIGHTS,

    /**
     * Costs for maintaining and repairing equipment and facilities.
     */
    MAINTENANCE_REPAIRS,

    /**
     * Ongoing income from intellectual property rights.
     */
    ROYALTY_INCOME,

    /**
     * Performance-based payments to employees or contractors.
     */
    BONUSES,

    /**
     * Revenue from selling or licensing content to international markets.
     */
    INTERNATIONAL_SALES,

    /**
     * Income from selling physical media (DVDs, Blu-rays, etc.).
     */
    PHYSICAL_MEDIA_SALES,

    /**
     * Revenue from digital downloads and video-on-demand services.
     */
    DIGITAL_SALES,

    /**
     * Income from awards, grants, and competition prizes.
     */
    AWARDS_GRANTS,

    /**
     * Revenue from product placement deals within productions.
     */
    PRODUCT_PLACEMENT,

    /**
     * Payments made to repay borrowed money and interest.
     */
    LOAN_REPAYMENT,

    /**
     * Profits distributed to shareholders or investors.
     */
    DIVIDEND_PAYMENT,

    /**
     * Funds received from government incentives or tax rebates.
     */
    TAX_INCENTIVES,

    /**
     * Money received through crowdfunding campaigns.
     */
    CROWDFUNDING,

    /**
     * Transfers of funds between different accounts or departments within the studio.
     */
    INTERNAL_TRANSFER,

    /**
     * Manual corrections or adjustments to account balances.
     */
    MANUAL_ADJUSTMENT,

    /**
     * Emergency funds set aside for unexpected expenses or opportunities.
     */
    CONTINGENCY_FUND,

    /**
     * Funds allocated for research and development of new technologies or techniques.
     */
    RESEARCH_DEVELOPMENT,

    /**
     * Money returned due to cancelled contracts, refunds, or chargebacks.
     */
    REFUNDS_CHARGEBACKS,

    /**
     * Penalties paid for contract violations, late deliveries, or regulatory infractions.
     */
    PENALTIES_FINES,

    /**
     * Charitable donations made by the studio.
     */
    CHARITABLE_DONATIONS,

    /**
     * Costs associated with acquiring other companies or intellectual properties.
     */
    ACQUISITIONS,

    /**
     * Revenue from selling studio assets, equipment, or intellectual property.
     */
    ASSET_SALES,

    /**
     * Technology infrastructure costs (servers, cloud services, software subscriptions).
     */
    TECHNOLOGY_INFRASTRUCTURE,

    /**
     * Costs for employee training, workshops, and skill development.
     */
    TRAINING_COSTS
}
